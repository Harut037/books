package com.appware_system.books.controller;

import com.appware_system.books.model.domain.*;
import com.appware_system.books.model.entity.BookEntity;
import com.appware_system.books.model.entity.ReviewEntity;
import com.appware_system.books.model.enums.Categories;
import com.appware_system.books.service.BooksService;
import com.appware_system.books.service.UserService;
import com.appware_system.books.service.impl.JwtServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/User")
public class UserController {
    final String EMAIL_REGEXP = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$";
    private final UserService userService;
    private final BooksService booksService;
    private final JwtServiceImpl jwtService;

    @Autowired
    public UserController(UserService userService, BooksService booksService, JwtServiceImpl jwtService) {
        this.userService = userService;
        this.booksService = booksService;
        this.jwtService = jwtService;
    }

    @GetMapping(value = "/getInfo")
    public User getInfo(@RequestHeader(value = "Authorization") String authorizationToken) {
        String email = jwtService.extractUsername(authorizationToken.substring(7));
        if (email == null)
            throw new UsernameNotFoundException("No Such User");
        return userService.getInfo(email);
    }

    @PutMapping("/editInfo")
    public String editInfo(@RequestHeader(value = "Authorization") String authorizationToken,
                           @Valid @RequestBody @NonNull EditInfo editInfo) {
        String email = jwtService.extractUsername(authorizationToken.substring(7));
        if (email == null) {
            throw new UsernameNotFoundException("No Such User");
        } else {
            return userService.editInfo(editInfo, email);
        }
    }

    @PutMapping("/changePassword")
    public String changePassword(@RequestHeader(value = "Authorization") String authorizationToken,
                                 @Valid @RequestBody @NonNull PasswordChange passwordChange) {
        String email = jwtService.extractUsername(authorizationToken.substring(7));
        if (email == null) {
            throw new UsernameNotFoundException("No Such User");
        }
        if (!passwordChange.getOldPassword().equals(passwordChange.getNewPassword())) {
            if (userService.passwordChange(email, passwordChange.getOldPassword(), passwordChange.getNewPassword())) {
                return "Success";
            } else {
                throw new IllegalArgumentException("Error Occurred");
            }
        } else {
            throw new IllegalArgumentException("New Password Should Be Different From Old Password");
        }
    }

    @PutMapping("/resetPassword")
    public Boolean resetPassword(@RequestHeader(value = "Authorization") String authorizationToken,
                                 @Valid @RequestBody @NonNull PasswordChange passwordChange) {
        String email = jwtService.extractUsername(authorizationToken.substring(7));
        if (email == null) {
            throw new UsernameNotFoundException("No Such User");
        } else {
            return userService.resetChange(email, passwordChange.getNewPassword());
        }
    }

    @PutMapping("/changeEmail")
    public Boolean changeEmail(@RequestHeader(value = "Authorization") String authorizationToken,
                               @RequestBody @NonNull String newEmail) {
        String email = jwtService.extractUsername(authorizationToken.substring(7));
        if (email == null) {
            throw new UsernameNotFoundException("No Such User");
        }
        if (!newEmail.matches(EMAIL_REGEXP)) {
            throw new IllegalArgumentException("Invalid email");
        } else {
            return userService.changeEmail(email, newEmail);
        }
    }

    @GetMapping("/logout")
    public String logout(@RequestHeader(value = "Authorization") String authorizationToken) {
        jwtService.invalidateToken(authorizationToken.substring(7));
        return authorizationToken;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BookEntity>> getAll() {
        try {
            List<BookEntity> books = booksService.getAll();

            if (books.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    @GetMapping("/get/{booksId}")
    public Optional<BookEntity> get(@PathVariable("booksId") Long booksId) {
        return booksService.get(booksId);
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
    public ResponseEntity<List<BookEntity>> getByCategories(@PathVariable("category") Categories category) {
        try {
            List<BookEntity> books = booksService.getByCategory(category);
            if (books.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    @PostMapping("/addReview")
    public ResponseEntity<String> addReview(@RequestBody ReviewEntity review) {
        try {
            booksService.addReview(review);
            return ResponseEntity.ok("Review added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding review");
        }
    }


    
    @PutMapping("/addRating/{id}/{rating}")
    public ResponseEntity<String> addRating(@PathVariable("id") Long id, @PathVariable("rating") double rating) {
        try {
            booksService.addRating(id, rating);
            return ResponseEntity.ok("Rating added successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid rating");
        }
    }

}
