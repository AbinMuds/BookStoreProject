package com.example.bookstoreproject.author.service;

import com.example.bookstoreproject.author.dao.AuthorRepository;
import com.example.bookstoreproject.author.domain.Author;
import com.example.bookstoreproject.books.dao.BooksRepository;
import com.example.bookstoreproject.books.domain.Books;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BooksRepository booksRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    public List<Books> getBooksByAuthorId(Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()){
        return booksRepository.findByAuthor(optionalAuthor.get());
        }
        return new ArrayList<Books>();
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Author addBook(Author author, Books books) {
        author.addBook(books);
        return authorRepository.save(author);
    }

    public Author findByFirstNameAndLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Optional<Author> getAuthorByName(String firstName, String lastName) {
        return Optional.ofNullable(authorRepository.findByFirstNameAndLastName(firstName, lastName));
    }
}
