package com.appware_system.books.security.service;

import com.appware_system.books.model.entity.UserEntity;
import com.appware_system.books.security.dto.JwtAuthenticationResponse;
import com.appware_system.books.security.dto.SignInRequest;
import com.appware_system.books.security.dto.SignUpRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    UserEntity signUp(SignUpRequest signUpRequest);

     JwtAuthenticationResponse signIn(SignInRequest signInRequest);

}
