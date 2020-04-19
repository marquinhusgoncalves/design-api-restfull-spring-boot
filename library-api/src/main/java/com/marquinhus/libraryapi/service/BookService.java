package com.marquinhus.libraryapi.service;

import com.marquinhus.libraryapi.model.entiy.Book;

import java.util.Optional;

public interface BookService {
    Book save(Book any);

    Optional<Book> getById(Long id);

    void delete(Book book);

    Book update(Book book);
}
