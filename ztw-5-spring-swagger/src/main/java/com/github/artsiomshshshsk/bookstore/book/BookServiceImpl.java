package com.github.artsiomshshshsk.bookstore.book;


import com.github.artsiomshshshsk.bookstore.author.AuthorService;
import com.github.artsiomshshshsk.bookstore.book.BookController.BookCreateRequest;
import com.github.artsiomshshshsk.bookstore.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService {

    private final List<Book> booksRepo = new ArrayList<>();
    private int nextBookId = 0;

    private final AuthorService authorService;

    public BookServiceImpl(AuthorService authorService) {
        this.authorService = authorService;
        booksRepo.add(new Book(nextBookId++, "The Shining", 0, 447, null));
        booksRepo.add(new Book(nextBookId++, "The Hobbit", 1, 310, null));
        booksRepo.add(new Book(nextBookId++, "A Game of Thrones", 2, 694, null));
    }

    @Override
    public List<Book> getBooks() {
        return booksRepo;
    }

    @Override
    public Book getBookById(int id) {
        return booksRepo.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    @Override
    public Book addBook(BookCreateRequest bookCreateRequest) {
        var author = authorService.getAuthorById(bookCreateRequest.authorId());
        var book = new Book(nextBookId++, bookCreateRequest.title(), author.getId(), bookCreateRequest.pages(), null);
        booksRepo.add(book);
        return book;
    }


    @Override
    public Book updateBook(int id, BookController.BookUpdateRequest bookCreateRequest) {
        var author = authorService.getAuthorById(bookCreateRequest.authorId());
        return Optional.of(getBookById(id))
                .map(book -> {
                    Optional.ofNullable(bookCreateRequest.title()).ifPresent(book::setTitle);
                    Optional.ofNullable(bookCreateRequest.pages()).ifPresent(book::setPages);
                    book.setAuthorId(author.getId());
                    return book;
                })
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }


    @Override
    public void deleteBook(int id) {
        booksRepo.stream().filter(book -> book.getId() == id)
                .findFirst()
                .ifPresentOrElse(booksRepo::remove, () -> {
                    throw new NotFoundException("Book not found");
                });
    }
}
