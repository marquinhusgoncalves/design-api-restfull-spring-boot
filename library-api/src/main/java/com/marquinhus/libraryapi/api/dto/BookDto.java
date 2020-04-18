package com.marquinhus.libraryapi.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDto {
    private Number id;
    private String title;
    private String author;
    private String isbn;
}
