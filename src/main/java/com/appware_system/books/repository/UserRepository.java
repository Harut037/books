package com.appware_system.books.repository;

import com.appware_system.books.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Transactional
    @Query("SELECT u FROM UserEntity u  WHERE u.email = :email")
    Optional<UserEntity> findByEmail(final String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.password = :newPassword WHERE u.email = :email")
    int resetPassword(final String email, final String newPassword);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.email = :newEmail WHERE u.email = :email")
    int updateEmail(String email, String newEmail);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.firstName = :newFirstName WHERE u.email = :email")
    void updateFirstName(String newFirstName, String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.lastName = :newLastName WHERE u.email = :email")
    void updateLastName(String newLastName, String email);

    @Transactional
    @Query("SELECT u.password FROM UserEntity u WHERE u.email = :email")
    String getPassword(String email);
}
