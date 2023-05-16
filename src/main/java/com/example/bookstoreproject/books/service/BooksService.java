package com.example.bookstoreproject.books.service;

import com.example.bookstoreproject.books.dao.BooksRepository;
import com.example.bookstoreproject.books.domain.Books;
import com.example.bookstoreproject.category.domain.Category;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BooksService {
    @Autowired
    private BooksRepository booksRepository;
    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    public Optional<Books> getBooksById(Long id){
        return booksRepository.findById(id);
    }
//    public Books insertBooks(Books book) {
//        return booksRepository.save(book);
//    }

    public String deleteBooksById(Long id){
        booksRepository.deleteById(id);
        boolean exists = booksRepository.existsById(id);
        if(!exists){
            booksRepository.deleteById(id);
            return "deleted";
        }
        return "not found id";
    }
    public Books updateBook(Books books) {
      return booksRepository.save(books);
    }

    public Books save(Books books) {
        return booksRepository.save(books);
    }

    public List<Books> getBooksByCategory(Category newCategory) {
        return booksRepository.findByCategory(newCategory);
    }
}
