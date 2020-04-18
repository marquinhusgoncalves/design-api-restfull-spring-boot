package com.marquinhus.libraryapi.api.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Number id;
    private String title;
    private String author;
    private String isbn;
}
