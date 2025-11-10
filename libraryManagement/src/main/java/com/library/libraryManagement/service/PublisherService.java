package com.library.libraryManagement.service;

import com.library.libraryManagement.entity.Author;
import com.library.libraryManagement.entity.Publisher;

import java.util.List;

public interface PublisherService {
    public List<Publisher> findAll();

    public Publisher findById(int theId);

    public void save(Publisher thePublisher);

    public void deleteById(int theId);
}
