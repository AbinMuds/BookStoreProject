package com.example.bookstoreproject.category.service;

import com.example.bookstoreproject.books.dao.BooksRepository;
import com.example.bookstoreproject.books.domain.Books;
import com.example.bookstoreproject.category.dao.CategoryRepository;
import com.example.bookstoreproject.category.domain.Category;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BooksRepository booksRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Long id){
        categoryRepository.deleteById(id);
    }

    public List<Books> getBooksByCategoryId(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()){
            return booksRepository.findByCategory(optionalCategory.get());
        }
        return new ArrayList<Books>();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category addBook(Category category, Books books) {
        category.addBooks(books);
        return categoryRepository.save(category);
    }

    public Category findByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    public Optional<Category> getCategoryByName(String categoryName) {
        return Optional.ofNullable(categoryRepository.findByCategoryName(categoryName));
    }
//public Optional<Category> getCategoryByName(String categoryName) {
//    TypedQuery<Category> query = entityManager.createQuery(
//            "SELECT c FROM Category c WHERE c.categoryName = :categoryName", Category.class);
//    query.setParameter("categoryName", categoryName);
//    try {
//        Category category = query.getSingleResult();
//        return Optional.of(category);
//    } catch (NoResultException | NonUniqueResultException ex) {
//        return Optional.empty();
//    }
//}
}
