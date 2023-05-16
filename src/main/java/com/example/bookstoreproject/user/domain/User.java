package com.example.bookstoreproject.user.domain;

import com.example.bookstoreproject.author.domain.Author;
import com.example.bookstoreproject.books.domain.Books;
import com.example.bookstoreproject.category.domain.Category;
import jakarta.persistence.*;
import lombok.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Table(name="user")
public class User {
    @Id
    @SequenceGenerator(name="category_sequence", sequenceName = "category_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_sequence")
    private Long u_id;

    private String userName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Books> favouriteBooks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_categories",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> favouriteCategories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_authors",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> favouriteAuthors = new ArrayList<>();

}
