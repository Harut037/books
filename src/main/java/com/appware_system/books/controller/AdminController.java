package com.appware_system.books.controller;

import com.appware_system.books.model.domain.Book;
import com.appware_system.books.model.entity.BookEntity;
import com.appware_system.books.model.enums.Categories;
import com.appware_system.books.validations.ValidationForBook;
import com.appware_system.books.service.BooksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/Admin")
@RequiredArgsConstructor
public class AdminController {

    private final BooksService booksService;


    @GetMapping("/{booksId}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable("booksId") Long booksId) {
        Optional<BookEntity> book = booksService.get(booksId);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/books")
    public ResponseEntity<List<BookEntity>> getAllBooks() {
        List<BookEntity> allBooks = booksService.getAll();
        if (allBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allBooks);
    }


    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@Valid @RequestBody Book book) {
        if (booksService.add(book) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid book");
        }
    }
    @DeleteMapping("/{booksId}")
    public ResponseEntity<String> deleteBook(@PathVariable("booksId") Long id) {
        String result = booksService.delete(id);
        return result != null ?
                ResponseEntity.ok(result) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/books/sortByCost")
    public ResponseEntity<List<BookEntity>> sortByCost() {
        List<BookEntity> sortedBooks = booksService.sortByPrice();
        return ResponseEntity.ok(sortedBooks);
    }

    @GetMapping("/books/sortByRating")
    public ResponseEntity<List<BookEntity>> sortByRating() {
        List<BookEntity> sortedBooks = booksService.sortByRating();
        return ResponseEntity.ok(sortedBooks);
    }

    @GetMapping("/books/author/{authorName}")
    public ResponseEntity<List<BookEntity>> getByAuthorName(@PathVariable("authorName") String authorName) {
        List<BookEntity> booksByAuthor = booksService.getByAuthorName(authorName);
        if (booksByAuthor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booksByAuthor);
    }

    @GetMapping("/books/author/surname/{authorSurname}")
    public ResponseEntity<List<BookEntity>> getByAuthorSurname(@PathVariable("authorSurname") String authorSurname) {
        List<BookEntity> booksByAuthorSurname = booksService.getByAuthorSurname(authorSurname);
        if (booksByAuthorSurname.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booksByAuthorSurname);
    }

    @GetMapping("/books/category/{category}")
    public ResponseEntity<List<BookEntity>> getByCategory(@PathVariable("category") Categories category) {
        List<BookEntity> booksByCategory = booksService.getByCategory(category);
        if (booksByCategory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booksByCategory);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long id, @RequestBody Book book) {
        String result = booksService.updateBook(id, book);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ID " + id + " not found");
        }
    }
}
