package com.appware_system.books.repository;


import com.appware_system.books.model.entity.BookEntity;
import com.appware_system.books.model.enums.BooksLanguage;
import com.appware_system.books.model.enums.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<BookEntity, Long> {

    @Query("SELECT b FROM BookEntity b ORDER BY b.price ASC")
    List<BookEntity> findAllBooksOrderedByPriceAsc();

    @Query("SELECT a FROM BookEntity a ORDER BY a.rating DESC")
    List<BookEntity> findAllBooksOrderedByRatingDesc();


    List<BookEntity> findBookEntitiesByCategory(Categories category);

    List<BookEntity> findBookEntitiesByAuthorName(String authorName);

    List<BookEntity> findBookEntitiesByAuthorSurname(String surName);

    @Query("UPDATE BookEntity t SET t.authorName = :authorName where t.id = :id")
    void updateAuthorName(String authorName, Long id);
    @Query("UPDATE BookEntity t SET t.authorSurname = :authorSurname where t.id = :id")
    void updateAuthorSurname(String authorSurname, Long id);
    @Query("UPDATE BookEntity t SET t.category = :category where t.id = :id")
    void updateCategory(Categories category, Long id);
    @Query("UPDATE BookEntity t SET t.originalLanguage = :originalLanguage where t.id = :id")
    void updateLanguage(BooksLanguage originalLanguage, Long id);

    @Query("UPDATE BookEntity  t set t.price = :price where t.id = :id")
    void updatePrice(double price, Long id);

    @Query("UPDATE BookEntity  t set t.rating = :rating where t.id = :id")
    void updateRating(double rating, Long id);

    @Query("UPDATE BookEntity t SET t.year = :year where t.id = :id")
    void updateYear(Date year, Long id);

}

