package com.github.artsiomshshshsk.bookstore.author;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Author {
    private int id;
    private String name;
    private String surname;
}