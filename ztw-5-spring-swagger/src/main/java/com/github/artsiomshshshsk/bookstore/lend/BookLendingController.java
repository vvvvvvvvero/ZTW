package com.github.artsiomshshshsk.bookstore.lend;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(name = "Book Lending", description = "Book lending API")
@RequestMapping("/v1/lendings")
public record BookLendingController(BookLendingService bookLendingService) {

    @Operation(
            summary = "Lend book",
            description = "Lend book to user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully lent the book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Book already lent"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public void lendBook(@Valid @RequestBody BookLendingController.CreateBookLendingRequest createBookLendingRequest) {
        bookLendingService.lendBook(createBookLendingRequest.bookId(), createBookLendingRequest.lenderId());
    }


    @Operation(
            summary = "Return book",
            description = "Return book to library"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned the book"),
            @ApiResponse(responseCode = "404", description = "Lending not found")
    })
    @PostMapping("/return")
    public void returnBook(@Valid @RequestBody BookLendingController.CreateBookLendingRequest createBookLendingRequest) {
        bookLendingService.returnBook(createBookLendingRequest.bookId(), createBookLendingRequest.lenderId());
    }


    @Operation(
            summary = "User Lendings",
            description = "Get all lendings for user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned lendings"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/lender/{lenderId}")
    public List<BookLending> getUserLendings(@PathVariable Integer lenderId) {
        return bookLendingService.getUserLendings(lenderId);
    }


    @Operation(
            summary = "Add book lender to the system",
            description = "Add new book lender"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added lender")
    })
    @PostMapping("/lender")
    public Lender addLender(@Valid @RequestBody CreateBookLenderRequest createBookLenderRequest) {
        return bookLendingService.addLender(createBookLenderRequest);
    }

    @Operation(
            summary = "Get all book lenders",
            description = "Get all book lenders from the bookstore"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of book lenders"),
    })
    @GetMapping("/lender")
    public List<Lender> getLenders() {
        return bookLendingService.getLenders();
    }

    @Validated
    public record CreateBookLendingRequest(@NotNull Integer bookId, @NotNull Integer lenderId) { }


    @Validated
    public record CreateBookLenderRequest(@NotNull @NotBlank String name,
                                          @NotNull @NotBlank String surname,
                                          @NotNull @NotBlank String email) { }
}
