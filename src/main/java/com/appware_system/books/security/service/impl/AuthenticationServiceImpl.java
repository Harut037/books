package com.appware_system.books.security.service.impl;

import com.appware_system.books.model.entity.UserEntity;
import com.appware_system.books.model.enums.Role;
import com.appware_system.books.repository.UserRepository;
import com.appware_system.books.security.dto.JwtAuthenticationResponse;
import com.appware_system.books.security.dto.SignInRequest;
import com.appware_system.books.security.dto.SignUpRequest;
import com.appware_system.books.security.service.AuthenticationService;
import com.appware_system.books.security.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public UserEntity signUp(SignUpRequest signUpRequest) {
        UserEntity userEntity = new UserEntity();
        
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity.setFirstName(signUpRequest.getFirstName());
        userEntity.setLastName(signUpRequest.getLastName());
        userEntity.setRole(Role.USER);
        userEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userRepository.save(userEntity);
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), passwordEncoder));

        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(()
                -> new IllegalArgumentException("Invalid email or password."));

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }


}
