package com.github.artsiomshshshsk.bookstore.author;


import com.github.artsiomshshshsk.bookstore.author.AuthorController.AuthorCreateRequest;
import com.github.artsiomshshshsk.bookstore.author.AuthorController.AuthorUpdateRequest;
import com.github.artsiomshshshsk.bookstore.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final List<Author> authorRepo = new ArrayList<>();

    private int nextId = 0;

    public AuthorServiceImpl() {
        authorRepo.add(new Author(nextId++, "Stephen", "King"));
        authorRepo.add(new Author(nextId++, "J.R.R.", "Tolkien"));
        authorRepo.add(new Author(nextId++, "George R.R.", "Martin"));
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepo;
    }


    @Override
    public Author getAuthorById(int id) {
        return authorRepo.stream()
                .filter(author -> author.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Author not found"));
    }


    @Override
    public Author addAuthor(AuthorCreateRequest authorCreateRequest) {
        var author = new Author(nextId++, authorCreateRequest.name(), authorCreateRequest.surname());
        authorRepo.add(author);
        return author;
    }


    @Override
    public Author updateAuthor(int id, AuthorUpdateRequest authorUpdateRequest) {
        return authorRepo.stream()
                .filter(author -> author.getId() == id)
                .findFirst()
                .map(author -> {
                    Optional.ofNullable(authorUpdateRequest.name()).ifPresent(author::setName);
                    Optional.ofNullable(authorUpdateRequest.surname()).ifPresent(author::setSurname);
                    return author;
                })
                .orElseThrow(() -> new NotFoundException("Author not found!"));
    }

    @Override
    public void deleteAuthor(int id) {
        authorRepo.stream().filter(author -> author.getId() == id)
                .findFirst()
                .ifPresentOrElse(authorRepo::remove, () -> {
                    throw new NotFoundException("Author not found!");
                });
    }
}
