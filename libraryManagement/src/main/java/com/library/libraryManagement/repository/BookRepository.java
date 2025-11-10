package com.library.libraryManagement.repository;

import com.library.libraryManagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    @Query("SELECT b FROM Book b WHERE b.bookName LIKE %:searchTerm% OR b.booksAuthor LIKE %:searchTerm%")
    List<Book> findBySearchTerm(@Param("searchTerm") String searchTerm);

}