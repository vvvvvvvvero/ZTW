package com.github.artsiomshshshsk.bookstore.author;

import com.github.artsiomshshshsk.bookstore.author.AuthorController.AuthorCreateRequest;
import com.github.artsiomshshshsk.bookstore.author.AuthorController.AuthorUpdateRequest;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthors();

    Author getAuthorById(int id);

    Author addAuthor(AuthorCreateRequest authorCreateRequest);

    Author updateAuthor(int id, AuthorUpdateRequest authorUpdateRequest);

    void deleteAuthor(int id);
}
