package com.appware_system.books.model.entity;

import com.appware_system.books.model.domain.Book;
import com.appware_system.books.model.enums.BooksLanguage;
import com.appware_system.books.model.enums.Categories;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Table(name = "books")
@Entity
@Data
@RequiredArgsConstructor
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name", nullable = false, length = 20)
    private String authorName;

    @Column(name = "author_surname", nullable = false, length = 20)
    private String authorSurname;

    @Column(name = "book_name", nullable = false, length = 30)
    private String bookName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Categories category;

    @Enumerated(EnumType.STRING)
    @Column(name = "origin_language", nullable = false)
    private BooksLanguage originalLanguage;

    @Column(name = "year_written", nullable = false)
    private Date year;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "rating", nullable = false)
    private double rating;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews = new ArrayList<>();


    public BookEntity(Book book) {
        this.authorName = book.getAuthorName();
        this.authorSurname = book.getAuthorSurname();
        this.bookName = book.getBookName();
        this.category = book.getCategory();
        this.originalLanguage = book.getOriginalLanguage();
        this.year = book.getYear();
        this.price = book.getPrice() + "$";
        this.rating = book.getRating();
    }
}

