package com.appware_system.books.model.entity;


import com.appware_system.books.model.domain.SignUpUser;
import com.appware_system.books.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;
    @OneToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;



    public UserEntity(SignUpUser signUpUser) {
        this.setFirstName(signUpUser.getFirstName());
        this.setLastName(signUpUser.getLastName());
        this.setEmail(signUpUser.getEmail());
        this.setPassword(signUpUser.getPassword());
        this.setStatus(Status.ACTIVE);
        this.setCreated(LocalDate.now());
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}