package com.appware_system.books.model.domain;


import com.appware_system.books.model.entity.UserEntity;
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

    public User(UserEntity userEntity) {
        this.email = userEntity.getEmail();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.password = userEntity.getPassword();
    }
}
