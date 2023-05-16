package com.example.bookstoreproject.user.controller;

import com.example.bookstoreproject.author.domain.Author;
import com.example.bookstoreproject.author.service.AuthorService;
import com.example.bookstoreproject.books.domain.Books;
import com.example.bookstoreproject.books.service.BooksService;
import com.example.bookstoreproject.category.domain.Category;
import com.example.bookstoreproject.category.service.CategoryService;
import com.example.bookstoreproject.user.domain.User;
import com.example.bookstoreproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BooksService booksService;

    @GetMapping("/{id}/favouriteBooks")
    public List<Books> getUserFavouriteBooks(@PathVariable Long id){
        return userService.getUserFavoriteBooks(id);
    }
    @PostMapping("/{userId}/favorite-categories")
    public ResponseEntity<Void> addFavoriteCategory(@PathVariable Long userId, @RequestBody Category category) {
        Optional<Category> existingCategory = categoryService.getCategoryByName(category.getCategoryName());
        Category newCategory;
        if (existingCategory.isPresent()) {
            newCategory = existingCategory.get();
            System.out.println("it went here");
        } else {
            newCategory = new Category(category.getCategoryName());
            categoryService.save(newCategory);
            System.out.println("it went to other here");
        }

        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.getFavouriteCategories().contains(newCategory)) { // Check if category already exists
                userService.addFavoriteCategory(userId, newCategory.getC_id());
                user.getFavouriteCategories().add(newCategory);
            }

            // get all the books that belong to the new category
            List<Books> booksInCategory = booksService.getBooksByCategory(newCategory);

            // add the books to the user's favorite books list if they're not already there
            for (Books book : booksInCategory) {
                if (!user.getFavouriteBooks().contains(book)) {
                    user.getFavouriteBooks().add(book);
                }
            }
            userService.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{userId}/favorite-authors")
    public ResponseEntity<Void> addFavouriteAuthor(@PathVariable Long userId, @RequestBody Author author){
        Optional<Author> existingAuthor = authorService.getAuthorByName(author.getFirstName(), author.getLastName());
        Author newAuthor;
        if(existingAuthor.isPresent()){
            newAuthor = existingAuthor.get();
        } else{
            newAuthor = new Author(author.getFirstName(), author.getLastName());
        }
        authorService.save(author);
        userService.addFavoriteAuthor(userId, newAuthor.getA_id());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestParam("id") Long id,@RequestBody User user){
        if(id!= user.getU_id()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(userService.updateUser(user),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }

}
