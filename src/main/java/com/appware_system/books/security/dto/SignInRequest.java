package com.appware_system.books.security.dto;

import lombok.Data;

@Data
public class SignInRequest {

    private String email;

    private String password;
}
