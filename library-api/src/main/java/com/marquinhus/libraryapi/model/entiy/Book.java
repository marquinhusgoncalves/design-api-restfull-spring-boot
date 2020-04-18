package com.marquinhus.libraryapi.model.entiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Number id;
    private String title;
    private String author;
    private String isbn;
}
