package com.appware_system.books.controller;


import com.appware_system.books.model.domain.AuthRequest;
import com.appware_system.books.model.domain.SignUpUser;
import com.appware_system.books.service.BooksService;
import com.appware_system.books.service.UserService;
import com.appware_system.books.service.impl.JwtServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String signUp(@Valid @RequestBody @NonNull SignUpUser signUpUser) {
        return userService.signUp(signUpUser);
    }

    @GetMapping("/signIn")
    public String signIn(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(@RequestBody @NonNull String email) {
        if (!email.matches(EMAIL_REGEXP)) {
           throw new IllegalArgumentException("Invalid email");
      }
        return userService.forgotPassword(email);
    }

}