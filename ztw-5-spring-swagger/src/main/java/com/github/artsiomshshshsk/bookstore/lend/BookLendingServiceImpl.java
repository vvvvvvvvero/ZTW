package com.github.artsiomshshshsk.bookstore.lend;

import com.github.artsiomshshshsk.bookstore.book.BookServiceImpl;
import com.github.artsiomshshshsk.bookstore.exception.IllegalArgumentException;
import com.github.artsiomshshshsk.bookstore.exception.NotFoundException;
import com.github.artsiomshshshsk.bookstore.lend.BookLendingController.CreateBookLenderRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookLendingServiceImpl implements BookLendingService {

    private final BookServiceImpl bookService;
    private final List<BookLending> bookLendings = new ArrayList<>();

    private final List<Lender> lenders = new ArrayList<>();

    private final int nextLenderId = 0;


    public BookLendingServiceImpl(BookServiceImpl bookService) {
        this.bookService = bookService;
    }


    @Override
    public void lendBook(int bookId, int userId) {
        var book = bookService.getBookById(bookId);
        if(book.getLenderId() != null) {
            throw new IllegalArgumentException("Book is already lent");
        }
        var lender = getLenderById(userId);
        var lending = new BookLending(book.getId(), lender.getId());
        bookLendings.add(lending);
        book.setLenderId(lender.getId());
    }

    @Override
    public void returnBook(Integer bookId, Integer userId) {
        var lending = findLending(bookId, userId);
        bookLendings.remove(lending);
    }

    private Lender getLenderById(int id) {
        return lenders.stream()
                .filter(lender -> lender.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Lender not found"));
    }

    private BookLending findLending(int bookId, int lenderId) {
        return bookLendings.stream()
                .filter(lending -> lending.getBookId() == bookId && lending.getLenderId() == lenderId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Lending not found"));
    }


    @Override
    public List<BookLending> getUserLendings(Integer userId) {
        var lender = getLenderById(userId);
        return bookLendings.stream()
                .filter(lending -> lending.getLenderId().equals(lender.getId()))
                .toList();
    }


    @Override
    public Lender addLender(CreateBookLenderRequest request) {
        var lender = new Lender(nextLenderId, request.name(), request.surname(), request.email());
        lenders.add(lender);
        return lender;
    }
}
