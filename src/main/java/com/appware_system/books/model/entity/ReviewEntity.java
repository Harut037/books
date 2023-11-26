package com.appware_system.books.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review")
@Data
@RequiredArgsConstructor
public class ReviewEntity  {

    @Column(name = "book_id")
    @Id
    private Long id;

    @Column(name = "review", length = 50)
    private String review;


}
