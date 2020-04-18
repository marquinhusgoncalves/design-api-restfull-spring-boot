package com.marquinhus.libraryapi.model.repository;

import com.marquinhus.libraryapi.model.entiy.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
