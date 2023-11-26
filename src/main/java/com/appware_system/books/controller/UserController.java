package com.appware_system.books.controller;

import com.appware_system.books.model.domain.EditInfo;
import com.appware_system.books.model.domain.PasswordChange;
import com.appware_system.books.model.domain.User;
import com.appware_system.books.model.entity.BookEntity;
import com.appware_system.books.model.enums.Categories;
import com.appware_system.books.service.BooksService;
import com.appware_system.books.service.UserService;
import com.appware_system.books.service.impl.JwtServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        this.booksService= booksService;
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
    public List<BookEntity> get() {
        return booksService.getAll();
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
    public List<BookEntity> getByCategories(@PathVariable("category") Categories category) {
        return booksService.getByCategory(category);
    }

    @PutMapping("/leaveReview")
    public String leaveReview(@RequestHeader(value = "Authorization") String authorizationToken,
                                 @Valid @RequestBody @NonNull PasswordChange passwordChange) {
        String email = jwtService.extractUsername(authorizationToken.substring(7));
        if (email == null) {
            throw new UsernameNotFoundException("No Such User");
        } else {
            return userService.resetChange(email, passwordChange.getNewPassword());
        }
    }

}
