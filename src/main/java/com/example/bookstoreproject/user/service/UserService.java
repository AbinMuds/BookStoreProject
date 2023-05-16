package com.example.bookstoreproject.user.service;

import com.example.bookstoreproject.author.dao.AuthorRepository;
import com.example.bookstoreproject.author.domain.Author;
import com.example.bookstoreproject.books.domain.Books;
import com.example.bookstoreproject.category.dao.CategoryRepository;
import com.example.bookstoreproject.category.domain.Category;
import com.example.bookstoreproject.user.dao.UserRepository;
import com.example.bookstoreproject.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public void addFavoriteCategory(Long userId, Long categoryId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (user.isPresent() && category.isPresent()) {
            user.get().getFavouriteCategories().add(category.get());
            userRepository.save(user.get());
        }
    }

    public void addFavoriteAuthor(Long userId, Long authorId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Author> author = authorRepository.findById(authorId);
        if (user.isPresent() && author.isPresent()){
            user.get().getFavouriteAuthors().add(author.get());
            userRepository.save(user.get());
        }
    }

    public List<Books> getUserFavoriteBooks(Long id){
        Optional<User> user = userRepository.findById(id);
        List<Category> categories = user.get().getFavouriteCategories();
        List<Author> authors = user.get().getFavouriteAuthors();
        return userRepository.findAllByFavouriteCategoriesInAndFavouriteAuthorsIn(categories, authors)
                .stream().flatMap(u -> u.getFavouriteBooks().stream()).collect(Collectors.toList());
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
