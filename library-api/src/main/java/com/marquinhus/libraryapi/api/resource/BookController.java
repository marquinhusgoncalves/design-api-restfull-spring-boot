package com.marquinhus.libraryapi.api.resource;

import com.marquinhus.libraryapi.api.dto.BookDto;
import com.marquinhus.libraryapi.model.entiy.Book;
import com.marquinhus.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody BookDto dto) {
        Book entity = Book.builder()
                .author(dto.getAuthor())
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .build();

        entity = service.save(entity);

        return BookDto.builder()
                .id(entity.getId())
                .author(entity.getAuthor())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .build();
    }
}
