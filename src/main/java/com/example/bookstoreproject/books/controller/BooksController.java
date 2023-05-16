package com.example.bookstoreproject.books.controller;

import com.example.bookstoreproject.author.domain.Author;
import com.example.bookstoreproject.author.service.AuthorService;
import com.example.bookstoreproject.books.domain.Books;
import com.example.bookstoreproject.books.service.BooksService;
import com.example.bookstoreproject.category.domain.Category;
import com.example.bookstoreproject.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/books")
public class BooksController {
    @Autowired
    private BooksService booksService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public List<Books> getAllBooks() {
        return booksService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Books> getBooksById(@PathVariable Long id) {
        Optional<Books> book = booksService.getBooksById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Books> insertBooks(@RequestBody Books books){
        Author author = books.getAuthor();
        Category category = books.getCategory();

        Author existingAuthor =authorService.findByFirstNameAndLastName(author.getFirstName(), author.getLastName());
        if(existingAuthor!= null) {
            author = existingAuthor;
        }else {
            author = authorService.save(author);
        }

        Category existingCategory = categoryService.findByCategoryName(category.getCategoryName());
        if (existingCategory != null) {
            category = existingCategory;
        } else {
            category = categoryService.save(category);
        }

        books.setAuthor(author);
        books.setCategory(category);

        books = booksService.save(books);
        category = categoryService.addBook(category, books);
        author = authorService.addBook(author, books);
        return ResponseEntity.ok(books);
    }

    @DeleteMapping(path="/{id}")
    public void deleteBooks(@PathVariable(value="id") Long id){
        booksService.deleteBooksById(id);
    }

    @PutMapping
    public ResponseEntity updateBooks (@RequestParam("id") Long id,@RequestBody Books books){
        if(id!= books.getB_id()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(booksService.updateBook(books),HttpStatus.OK);
    }
}
