package com.github.artsiomshshshsk.bookstore.lend;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Lender {
    private Integer id;
    private String name;
    private String surname;
    private String email;
}
