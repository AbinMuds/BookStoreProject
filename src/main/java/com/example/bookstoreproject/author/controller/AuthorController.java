package com.example.bookstoreproject.author.controller;

import com.example.bookstoreproject.author.domain.Author;
import com.example.bookstoreproject.author.service.AuthorService;
import com.example.bookstoreproject.books.domain.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Optional<Author> getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @PutMapping
    public ResponseEntity updateAuthor (@RequestParam("id") Long id, @RequestBody Author author){
        if(id!= author.getA_id()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(authorService.updateAuthor(author),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
    }

    @GetMapping("/{id}/books")
    public List<Books> getBooksByAuthorId(@PathVariable Long id) {
        return authorService.getBooksByAuthorId(id);
    }
}

