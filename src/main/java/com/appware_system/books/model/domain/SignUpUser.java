package com.appware_system.books.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import jakarta.validation.constraints.Pattern;


@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SignUpUser {

    @NonNull
    @Pattern(regexp = "[A-Z][a-z]+", message = "Invalid First Name Pattern")
    private String firstName;
    @NonNull
    @Pattern(regexp = "[A-Z][a-z]+", message = "Invalid Last Name Pattern")
    private String lastName;
    @NonNull
    @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@gmail\\.com$", message = "Invalid Email Pattern")
    private String email;
    @NonNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+])[a-zA-Z0-9!@#$%^&*()_+]{8,20}$", message = "Invalid Password Pattern")
    private String password;


}