package com.github.artsiomshshshsk.bookstore.author;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Author", description = "Author API")
@RequestMapping("/v1/authors")
@Slf4j
public record AuthorController (AuthorService authorService) {

    @Operation(
            summary = "Get all authors",
            description = "Get all authors from the bookstore"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of authors"),
    })
    @GetMapping
    public List<Author> getAuthors() {
        log.info("User requested all authors");
        return authorService.getAuthors();
    }


    @Operation(
            summary = "Get author by id",
            description = "Get author from the bookstore by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the author"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable int id) {
        log.info("User requested author with id: {}", id);
        return authorService.getAuthorById(id);
    }



    @Operation(
            summary = "Add author",
            description = "Add author to the bookstore"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the author"),
    })
    @PostMapping
    public Author addAuthor(@Valid @RequestBody AuthorCreateRequest authorCreateRequest) {
        log.info("User requested to add author: {}", authorCreateRequest);
        return authorService.addAuthor(authorCreateRequest);
    }


    @Operation(
            summary = "Update author",
            description = "Update author in the bookstore"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the author"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable int id, @Valid @RequestBody AuthorUpdateRequest authorUpdateRequest) {
        log.info("User requested to update author with id: {}", id);
        return authorService.updateAuthor(id, authorUpdateRequest);
    }


    @Operation(
            summary = "Delete author",
            description = "Delete author from the bookstore"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the author"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable int id) {
        log.info("User requested to delete author with id: {}", id);
        authorService.deleteAuthor(id);
    }


    @Validated
    public record AuthorCreateRequest(
            @NotBlank @NotNull String name,
            @NotBlank @NotNull String surname
    ) { }

    public record AuthorUpdateRequest(
            String name,
            String surname
    ) { }

}
