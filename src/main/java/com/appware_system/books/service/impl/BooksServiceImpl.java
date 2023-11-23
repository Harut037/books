package com.appware_system.books.service.impl;

import com.appware_system.books.model.domain.Book;
import com.appware_system.books.model.entity.BookEntity;
import com.appware_system.books.model.enums.BooksLanguage;
import com.appware_system.books.model.enums.Categories;
import com.appware_system.books.repository.BooksRepository;
import com.appware_system.books.service.BooksService;
import com.appware_system.books.validations.ValidationForBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;


    @Autowired
    public BooksServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
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
    public String updateBook(Long id, Book book) {
        ValidationForBook vfb = new ValidationForBook();
        Optional<BookEntity> bookEntity = booksRepository.findById(id);
        if (bookEntity.isPresent()) {
            if (vfb.isValidAuthorName(book.getAuthorName()) != null) {
                booksRepository.updateAuthorName(book.getAuthorName(), bookEntity.get().getId());
            }
            if (vfb.isValidAuthorSurname(book.getAuthorSurname()) != null) {
                booksRepository.updateAuthorSurname(book.getAuthorSurname(), bookEntity.get().getId());
            }
            if (vfb.isValidateDate(book.getYear()) != null) {
                booksRepository.updateYear(book.getYear(), id);
            }
            if (book.getPrice() != 0.0) {
                booksRepository.updatePrice(book.getPrice(), id);
            }
            if (book.getRating() != 0.0) {
                booksRepository.updateRating(book.getRating(), id);
            }
           if (book.getCategory() != null) {
////                bookEntity.get().setCategory(Categories.valueOf(book.getCategory().toString()));
//                if (book.getOriginalLanguage() != null) {
//                    bookEntity.get().setOriginalLanguage(BooksLanguage.valueOf(book.getOriginalLanguage().toString()));
//                }
            }
        }
        return "The book has updated successfully";
    }
}
