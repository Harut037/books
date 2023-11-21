package com.appware_system.books.controller;

import com.appware_system.books.model.domain.Book;
import com.appware_system.books.model.entity.BookEntity;
import com.appware_system.books.model.enums.Categories;
import com.appware_system.books.validations.ValidationForBook;
import com.appware_system.books.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/books")
public class BooksController {


    private final BooksService booksService;


    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }


    @GetMapping("/get/{booksId}")
    public Optional<BookEntity> get(@PathVariable("booksId") Long booksId) {
        return booksService.get(booksId);
    }


    @GetMapping("/getAll")
    public List<BookEntity> get() {
        return booksService.getAll();
    }

    @PostMapping("/add")
    public String add(@RequestBody Book book) {
        ValidationForBook vfb = new ValidationForBook();
        if (vfb.isValidBook(book)) {
            return booksService.add(book);
        }
        return "Invalid book";
    }

    @PutMapping("/delete/{booksId}")
    public String delete(@PathVariable("booksId") Long id) {
        return booksService.delete(id);
    }

    @GetMapping("/get/sortByCost")
    public List<BookEntity> sortByPrice() {
        return booksService.sortByPrice();
    }

    @GetMapping("/get/sortByRating")
    public List<BookEntity> sortByRating() {
        return booksService.sortByRating();
    }

    @GetMapping("/getByName/{authorName}")
    public List<BookEntity> getByAuthorName(@PathVariable("authorName") String authorName) {
        return booksService.getByAuthorName(authorName);
    }

    @GetMapping("/getBySurname/{authorSurname}")
    public List<BookEntity> getByAuthorSurname(@PathVariable("authorSurname") String authorSurname) {
        return booksService.getByAuthorSurname(authorSurname);
    }

    @GetMapping("/getByCategory/{category}")
    public List<BookEntity> getByCategories(@PathVariable("category") Categories category) {
        return booksService.getByCategory(category);
    }

    @PutMapping("update/{booksId}")
    public String update(@PathVariable("booksId") Long id, @RequestBody Book book) {
        return booksService.updateBook(id, book);
    }
}
