package com.example.bookstoreproject.category.controller;

import com.example.bookstoreproject.books.domain.Books;
import com.example.bookstoreproject.category.domain.Category;
import com.example.bookstoreproject.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
    }

    @PutMapping
    public ResponseEntity updateCategory(@RequestParam("id") Long id, @RequestBody Category category){
        if(id!=category.getC_id()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(categoryService.updateCategory(category),HttpStatus.OK);
    }

    @GetMapping("/{id}/books")
    public List<Books> getBooksByCategoryId(@PathVariable Long id){
        return categoryService.getBooksByCategoryId(id);
    }

}
