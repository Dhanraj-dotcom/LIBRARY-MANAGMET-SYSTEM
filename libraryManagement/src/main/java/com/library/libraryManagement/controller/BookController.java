package com.library.libraryManagement.controller;

import com.library.libraryManagement.entity.Book;
import com.library.libraryManagement.service.AuthorService;
import com.library.libraryManagement.service.BookService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookController {

    // Injecting dependencies using constructor
    final BookService bookService;
    // Note: AuthorService is imported but not used, so the constructor is left as is.
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/list")
    public String listBooks(Model theModel) {
        // Correct: List uses the plural name "books"
        List<Book> theBooks = bookService.findAll();
        theModel.addAttribute("books", theBooks);
        return "list-books";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Book theBook = new Book();
        // FIX: Changed attribute name from "books" to "book" (singular)
        theModel.addAttribute("book", theBook);
        return "book-form";
    }


    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("bookId") int theID, Model theModel) {
        Book theBook = bookService.findById(theID);
        // FIX: Changed attribute name from "books" to "book" (singular)
        theModel.addAttribute("book", theBook);
        return "book-form";
    }

    @PostMapping("/save")
    // FIX: Changed @ModelAttribute name from "books" to "book" (singular)
    public String saveBook(@ModelAttribute("book") Book theBook) {
        // save the book
        bookService.save(theBook);
        // use a redirect to prevent duplicate submissions
        return "redirect:/books/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bookId") int theId) {
        // delete the book
        bookService.deleteById(theId);
        return "redirect:/books/list";
    }

    @GetMapping("/search")
    public String findBookByName(Model model, @Param("keyword") String keyword){
        // get search results
        List<Book> searchResults = bookService.findBookByName(keyword);
        // Correct: List uses the plural name "books"
        model.addAttribute("books", searchResults);
        return "list-books";
    }
}