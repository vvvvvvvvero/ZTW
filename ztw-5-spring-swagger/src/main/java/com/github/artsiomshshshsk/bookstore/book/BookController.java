package com.github.artsiomshshshsk.bookstore.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
@Tag(name = "Book", description = "Book API")
@Slf4j
public record BookController(
        BookService bookService
) {

    @Operation(
            summary = "Get all books",
            description = "Get all books from the bookstore"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of books"),
    })
    @GetMapping
    public List<Book> getBooks() {
        log.info("User requested all books");
        return bookService.getBooks();
    }


    @Operation(
            summary = "Get book by id",
            description = "Get book from the bookstore by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the book"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        log.info("User requested book with id: {}", id);
        return bookService.getBookById(id);
    }


    @Operation(
            summary = "Add book",
            description = "Add book to the bookstore"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the book"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @PostMapping
    public Book addBook(@Valid @RequestBody BookController.BookCreateRequest bookCreateRequest) {
        log.info("User requested to add book: {}", bookCreateRequest);
        return bookService.addBook(bookCreateRequest);
    }

    @Operation(
            summary = "Update book",
            description = "Update book in the bookstore"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody BookUpdateRequest bookUpdateRequest) {
        log.info("User requested to update book with id: {} to: {}", id, bookUpdateRequest);
        return bookService.updateBook(id, bookUpdateRequest);
    }

    @Operation(
            summary = "Delete book",
            description = "Delete book from the bookstore"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the book"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id) {
        log.info("User requested to delete book with id: {}", id);
        bookService.deleteBook(id);
    }



    public record BookCreateRequest(@NotNull @NotBlank String title,
                                    @NotNull @NotBlank Integer authorId,
                                    @NotNull Integer pages) {
    }

    public record BookUpdateRequest(String title,
                                    Integer authorId,
                                    Integer pages) {
    }

    record BookResponse(Integer id, String title, Integer authorId, Integer pages) {}
}
