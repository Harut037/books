package com.appware_system.books.model.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
