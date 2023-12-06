package com.appware_system.books.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review")
@Data
@RequiredArgsConstructor
public class ReviewEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;


    public ReviewEntity(String reviewText, BookEntity book) {
        this.reviewText = reviewText;
        this.book = book;
    }
}
