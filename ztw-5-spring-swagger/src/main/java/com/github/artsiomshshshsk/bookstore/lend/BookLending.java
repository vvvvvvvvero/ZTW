package com.github.artsiomshshshsk.bookstore.lend;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class BookLending {
    private Integer lenderId;
    private Integer bookId;
}
