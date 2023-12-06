package com.appware_system.books.service.impl;


import com.appware_system.books.model.domain.EditInfo;
import com.appware_system.books.model.domain.SignUpUser;
import com.appware_system.books.model.domain.User;
import com.appware_system.books.model.entity.RoleEntity;
import com.appware_system.books.model.entity.UserEntity;
import com.appware_system.books.repository.UserRepository;
import com.appware_system.books.service.RoleService;
import com.appware_system.books.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtServiceImpl jwtService;
    private final RoleService roleService;



    /**
     * Retrieves the ID of the user associated with the specified email.
     *
     * @param email the email of the user
     * @return the ID of the user, or null if the user is not found
     */
    @Override
    public Long getIdByEmail(String email) {
        Optional<UserEntity> op = userRepository.findByEmail(email);
        return op.map(UserEntity::getId).orElse(null);
    }

    /**
     * Registers a new user based on the provided SignUpUser object.
     *
     * @param signUpUser the SignUpUser object containing the user's registration details
     * @return a string indicating the success of the registration process
     * @throws IllegalArgumentException if the email or phone number is already in use
     */
    @Override
    public String signUp(final SignUpUser signUpUser) {
        UserEntity userEntity = new UserEntity(signUpUser);
        Optional<UserEntity> op1 = userRepository.findByEmail(signUpUser.getEmail());
        if (op1.isPresent()) {
            throw new IllegalArgumentException("This Email Is Already In Use: " + op1.get().getEmail());
        }
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setAdmin(false);
        roleEntity.setUserRole(true);
        userEntity.setRoleEntity(roleEntity);
        roleService.saveRole(roleEntity);
        userRepository.save(userEntity);
        return "Success";
    }



    /**
     * Updates the user's information based on the provided EditInfo object.
     *
     * @param editInfo the EditInfo object containing the updated user information
     * @param email    the email of the user
     * @return a string indicating the success of the update operation
     * @throws IllegalArgumentException if all fields in the EditInfo object are null
     */
    @Override
    public String editInfo(final EditInfo editInfo, final String email) {
        boolean t = false;
        if (editInfo.getFirstName() != null) {
            t = true;
            userRepository.updateFirstName(editInfo.getFirstName(), email);
        }
        if (editInfo.getLastName() != null) {
            t = true;
            userRepository.updateLastName(editInfo.getLastName(), email);
        }
        if (t) {
            return "Edited Successfully";
        }
        throw new IllegalArgumentException("All Fields Are Null");
    }

    /**
     * Changes the password for the user with the specified email.
     *
     * @param email    the email of the user
     * @param oldPassword the old password to check
     * @param newPassword the new password to set
     * @return true if the password change was successful, false otherwise
     */
    @Override
    public Boolean passwordChange(String email, String oldPassword, String newPassword) {
        if(new BCryptPasswordEncoder().matches(oldPassword, userRepository.getPassword(email))){
            newPassword = new BCryptPasswordEncoder().encode(newPassword);
            return userRepository.resetPassword(email, newPassword) > 0;
        }
        throw new IllegalArgumentException("Old Password Is Incorrect");
    }


    /**
     * Initiates the password reset process for the user with the specified email.
     *
     * @param email the email of the user
     * @return a token generated for the password reset process
     * @throws IllegalArgumentException if no user is found with the specified email
     */
    @Override
    public String forgotPassword(String email) {
        Optional<UserEntity> op = userRepository.findByEmail(email);
        if (op.isPresent()) {
            return jwtService.generateToken(email);
        }
        throw new IllegalArgumentException("Not Found User With Such Email");
    }



    /**
     * Resets the password for the user with the specified email.
     *
     * @param email    the email of the user
     * @param password the new password to set
     * @return true if the password reset was successful, false otherwise
     */
    @Override
    public Boolean resetChange(String email, String password) {
        password = new BCryptPasswordEncoder().encode(password);
        return userRepository.resetPassword(email, password) > 0;
    }

    /**
     * Books a tour for the user with the specified email.
     *
     * @param bookTour the tour booking details
     * @param email    the email of the user
     * @return a message indicating the success of the booking
     * @throws IllegalArgumentException if the tour is not enabled for booking, or if the user has no card, or if the transaction is not successful
     */


    /**
     * Cancels the tour with the specified transaction number.
     *
     * @param transactionNumber the transaction number of the tour to be canceled
     * @return a message indicating the success of the cancellation
     * @throws IllegalArgumentException if the tour is not available for canceling or if there is an error reverting the transaction
     */


    /**
     * Retrieves the information of a user.
     *
     * @param email the email of the user
     * @return a User object containing the user's information, or null if the user is not found
     */
    @Override
    public User getInfo(String email) {
        Optional<UserEntity> op = userRepository.findByEmail(email);
        return op.map(User::new).orElse(null);
    }

    /**
     * Changes the email address of a user.
     *
     * @param email    the current email address of the user
     * @param newEmail the new email address to be set
     * @return true if the email address is successfully changed, false otherwise
     */
    @Override
    public Boolean changeEmail(String email, String newEmail) {
        return userRepository.updateEmail(email, newEmail) > 0;
    }

    /**
     * Changes the phone number of a user.
     *
     * @param email          the email address of the user
     * @param newPhoneNumber the new phone number to be set
     * @return true if the phone number is successfully changed, false otherwise
     */




    /**
     * Retrieves a user entity based on the provided email.
     *
     * @param email the email address of the user
     * @return the user entity
     * @throws NoSuchElementException if no user entity is found with the given email
     */
    @Override
    public UserEntity getUser(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            return userEntityOptional.get();
        } else {
            throw new NoSuchElementException("No user entity found with the email: " + email);
        }
    }




}

