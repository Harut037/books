package com.appware_system.books.controller;


import com.appware_system.books.model.domain.AuthRequest;
import com.appware_system.books.model.domain.SignUpUser;
import com.appware_system.books.service.BooksService;
import com.appware_system.books.service.UserService;
import com.appware_system.books.service.impl.JwtServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Guest")
public class GuestController {

    final String EMAIL_REGEXP = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@gmail\\.com$";
    private final BooksService booksService;
    private final UserService userService;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public GuestController(BooksService booksService, UserService userService, JwtServiceImpl jwtService, AuthenticationManager authenticationManager) {
        this.booksService = booksService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@Valid @RequestBody @NonNull SignUpUser signUpUser) {
        try {
            String signUpResult = userService.signUp(signUpUser);
            return ResponseEntity.ok(signUpResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during sign-up.");
        }
    }

    @GetMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getEmail());
                return ResponseEntity.ok(token);
            } else {
                throw new UsernameNotFoundException("Invalid credentials");
            }
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while signing in.");
        }
    }
    @GetMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody @NonNull String email) {
        try {
            if (!email.matches(EMAIL_REGEXP)) {
                throw new IllegalArgumentException("Invalid email");
            }
            String response = userService.forgotPassword(email);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }

}