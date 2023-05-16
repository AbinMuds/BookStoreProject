package com.example.bookstoreproject.category.domain;

import com.example.bookstoreproject.books.domain.Books;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="category")
public class Category {
    @Id
    @SequenceGenerator(name="category_sequence", sequenceName = "category_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_sequence")
    private Long c_id;
    private String categoryName;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Books> books = new ArrayList<>();

    public void addBooks(Books books) {
        books.setCategory(this);
        this.books.add(books);
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

}
