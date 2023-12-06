package com.appware_system.books.service.impl;

import com.appware_system.books.model.domain.Book;
import com.appware_system.books.model.entity.BookEntity;
import com.appware_system.books.model.entity.ReviewEntity;
import com.appware_system.books.model.enums.BooksLanguage;
import com.appware_system.books.model.enums.Categories;
import com.appware_system.books.repository.BooksRepository;
import com.appware_system.books.repository.ReviewRepository;
import com.appware_system.books.service.BooksService;
import com.appware_system.books.validations.ValidationForBook;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;
    private final ReviewRepository reviewRepository;


    @Autowired
    public BooksServiceImpl(BooksRepository booksRepository, ReviewRepository reviewRepository) {
        this.booksRepository = booksRepository;
        this.reviewRepository = reviewRepository;
    }


    @Override
    public Optional<BookEntity> get(Long id) {
        return booksRepository.findById(id);
    }

    @Override
    public String add(Book newBook) {
        BookEntity bookEntity = new BookEntity(newBook);
        booksRepository.save(bookEntity);
        return "The book has been successfully added";
    }

    @Override
    public String delete(Long id) {
        booksRepository.deleteById(id);
        return "The book has deleted successfully";
    }

    @Override
    public List<BookEntity> getAll() {
        return booksRepository.findAll();
    }

    @Override
    public List<BookEntity> sortByPrice() {
        return booksRepository.findAllBooksOrderedByPriceAsc();
    }

    @Override
    public List<BookEntity> sortByRating() {
        return booksRepository.findAllBooksOrderedByRatingDesc();
    }

    @Override
    public List<BookEntity> getByAuthorName(String authorName) {
        return booksRepository.findBookEntitiesByAuthorName(authorName);
    }

    @Override
    public List<BookEntity> getByAuthorSurname(String authorSurname) {
        return booksRepository.findBookEntitiesByAuthorSurname(authorSurname);
    }

    @Override
    public List<BookEntity> getByCategory(Categories category) {
        return booksRepository.findBookEntitiesByCategory(category);
    }

    @Override
    public String addRating(Long id, double rating) {
        if (rating <= 0 || rating >= 5) {
            throw new IllegalArgumentException("Rating should be between 1 and 5");
        }
        Optional<BookEntity> bookEntity = booksRepository.findById(id);
        if (bookEntity.isPresent()) {
            double result;
            if (bookEntity.get().getRating() == 0) {
                result = rating;
            } else {
                result = (bookEntity.get().getRating() + rating) / 2;
            }
            booksRepository.addRating(id, result);
        }
        return null;
    }

    @Override
    public String updateBook(Long id, Book book) {
        ValidationForBook vfb = new ValidationForBook();
        Optional<BookEntity> bookEntity = booksRepository.findById(id);
        if (bookEntity.isPresent()) {
            if (vfb.isValidAuthorName(book.getAuthorName()) != null) {
                booksRepository.updateAuthorName(book.getAuthorName(), id);
            }
            if (vfb.isValidAuthorSurname(book.getAuthorSurname()) != null) {
                booksRepository.updateAuthorSurname(book.getAuthorSurname(), id);
            }
            if (book.getYear() != null && vfb.isValidateDate(book.getYear()) != null) {
                booksRepository.updateYear(book.getYear(), id);
            }
            if (book.getPrice() != 0.0) {
                booksRepository.updatePrice(book.getPrice(), id);
            }
            if (book.getCategory() != null) {
                Categories categories = book.getCategory();
                booksRepository.updateCategory(categories, id);
            }
            if (book.getOriginalLanguage() != null) {
                BooksLanguage booksLanguage = book.getOriginalLanguage();
                booksRepository.updateLanguage(booksLanguage, id);
            }
        }
        return "The book has updated successfully";
    }

    @Override
    public void addReview(ReviewEntity review) {
        Optional<BookEntity> bookEntity = booksRepository.findById(review.getBook().getId());
        if (bookEntity.isPresent() && review.getReviewText() != null) {
            reviewRepository.save(review);
//            booksRepository.updateReview(review, review.getId());
        }
    }
}
