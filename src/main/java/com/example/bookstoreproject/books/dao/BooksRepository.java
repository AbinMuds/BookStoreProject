package com.example.bookstoreproject.books.dao;

import com.example.bookstoreproject.author.domain.Author;
import com.example.bookstoreproject.books.domain.Books;
import com.example.bookstoreproject.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    List<Books> findByAuthor(Author author);
    List<Books> findByCategory(Category category);
}
