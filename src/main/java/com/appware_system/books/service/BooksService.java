package com.appware_system.books.service;


import com.appware_system.books.model.domain.Book;
import com.appware_system.books.model.entity.BookEntity;
import com.appware_system.books.model.entity.ReviewEntity;
import com.appware_system.books.model.enums.Categories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface BooksService {

    Optional<BookEntity> get(Long id);

    List<BookEntity> getAll();

    String add(Book newBook);

    String delete(Long id);

    List<BookEntity> sortByPrice();

    List<BookEntity> sortByRating();

    List<BookEntity> getByAuthorName(String authorName);

    List<BookEntity> getByAuthorSurname(String authorSurname);

    List<BookEntity> getByCategory(Categories category);

    String addRating(Long id, double rating);

    String updateBook(Long id, Book book);

    void addReview(ReviewEntity review);


}
