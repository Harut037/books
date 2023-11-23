package com.appware_system.books.repository;

import com.appware_system.books.model.entity.UserEntity;
import com.appware_system.books.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

//    @Query("SELECT UserEntity from UserEntity t where  t.email = :email")
    Optional<UserEntity> findByEmail(String email);

    UserEntity findByRole(Role role);
}
