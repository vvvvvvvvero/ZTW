package com.github.artsiomshshshsk.bookstore.author;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Author", description = "Author API")
@RequestMapping("/v1/authors")
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
        authorService.deleteAuthor(id);
    }


    public record AuthorCreateRequest(
            @NotBlank @NotNull String firstName,
            @NotBlank @NotNull String lastName
    ) { }

    public record AuthorUpdateRequest(
            String firstName,
            String lastName
    ) { }

}
