package com.github.artsiomshshshsk.bookstore.lend;

import com.github.artsiomshshshsk.bookstore.lend.BookLendingController.CreateBookLenderRequest;

import java.util.List;

public interface BookLendingService {
    void lendBook(int bookId, int userId);

    void returnBook(Integer bookId, Integer userId);

    List<BookLending> getUserLendings(Integer userId);

    Lender addLender(CreateBookLenderRequest createBookLenderRequest);

    List<Lender> getLenders();
}
