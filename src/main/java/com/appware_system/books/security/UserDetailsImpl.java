package com.appware_system.books.security;


import com.appware_system.books.model.entity.UserEntity;
import com.appware_system.books.model.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private final String email;
    private final String password;
    private final List<GrantedAuthority> authorities;
    private final Status status;

    /**
     * Constructs a UserDetailsImpl object based on the given UserEntity.
     *
     * @param userEntity The UserEntity from which to extract user details.
     */
    public UserDetailsImpl(UserEntity userEntity) {
        email = userEntity.getEmail();
        password = userEntity.getPassword();
        this.authorities = Arrays.stream(userEntity.getRoleEntity().toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        status = userEntity.getStatus();
    }

    /**
     * Returns the collection of GrantedAuthority objects associated with the user.
     *
     * @return The collection of GrantedAuthority objects.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username (email) of the user.
     *
     * @return The username (email).
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Checks if the user account is non-expired.
     *
     * @return true if the account is non-expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return status != Status.EXPIRED;
    }

    /**
     * Checks if the user account is non-locked.
     *
     * @return true if the account is non-locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return status != Status.BLOCKED;
    }

    /**
     * Checks if the user credentials are non-expired.
     *
     * @return true if the credentials are non-expired, false otherwise.
     * In this implementation, the method always returns true as there is no logic for credential expiration.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks if the user account is enabled.
     *
     * @return true if the account is enabled (active), false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return status == Status.ACTIVE;
    }
    }
