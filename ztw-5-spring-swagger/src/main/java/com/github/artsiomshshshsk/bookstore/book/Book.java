package com.github.artsiomshshshsk.bookstore.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Book {
    private Integer id;
    private String title;
    private Integer authorId;
    private Integer pages;
    private Integer lenderId;
}
