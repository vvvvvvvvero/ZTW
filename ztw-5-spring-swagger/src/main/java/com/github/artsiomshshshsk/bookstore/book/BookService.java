package com.github.artsiomshshshsk.bookstore.book;

import com.github.artsiomshshshsk.bookstore.book.BookController.BookCreateRequest;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    Book getBookById(int id);

    Book addBook(BookCreateRequest bookCreateRequest);

    Book updateBook(int id, BookController.BookUpdateRequest bookCreateRequest);

    void deleteBook(int id);
}
