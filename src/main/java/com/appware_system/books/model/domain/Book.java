package com.appware_system.books.model.domain;

import com.appware_system.books.model.entity.BookEntity;
import com.appware_system.books.model.enums.BooksLanguage;
import com.appware_system.books.model.enums.Categories;
import lombok.*;
import org.springframework.lang.NonNull;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {


    private String authorName;

    private String authorSurname;

    private String bookName;

    private Categories category;

    private BooksLanguage originalLanguage;

    private Date year;

    private double price;

    private double rating;


    public Book(BookEntity bookEntity) {
        this.authorName = bookEntity.getAuthorName();
        this.authorSurname = bookEntity.getAuthorSurname();
        this.bookName = bookEntity.getBookName();
        this.category = bookEntity.getCategory();
        this.originalLanguage = bookEntity.getOriginalLanguage();
        this.year = bookEntity.getYear();
        this.price = Integer.valueOf(bookEntity.getPrice());
        this.rating = bookEntity.getRating();
    }

    public void setRating(double rating) {
        if (rating < 5 && rating > 0) {
            this.rating = rating;
        } else
            throw new IllegalArgumentException("Rating must be a number from 0 to 5 ");
    }

    public void setPrice(double price) {
        if (price < 150 && price > 2) {
            this.price = price;
        } else
            throw new IllegalArgumentException("The price cannot be lower than 2 and higher than 150");
    }
}

