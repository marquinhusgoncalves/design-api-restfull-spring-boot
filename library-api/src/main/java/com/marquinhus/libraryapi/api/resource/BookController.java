package com.marquinhus.libraryapi.api.resource;

import com.marquinhus.libraryapi.api.dto.BookDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody BookDto dto) {
        return dto;
    }
}
