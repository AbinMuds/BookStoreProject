package com.example.bookstoreproject.user.dao;

import com.example.bookstoreproject.author.domain.Author;
import com.example.bookstoreproject.category.domain.Category;
import com.example.bookstoreproject.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByFavouriteCategoriesInAndFavouriteAuthorsIn(List<Category> categories, List<Author> authors);
}
