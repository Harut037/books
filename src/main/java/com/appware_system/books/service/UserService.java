package com.appware_system.books.service;



import com.appware_system.books.model.domain.EditInfo;
import com.appware_system.books.model.domain.SignUpUser;
import com.appware_system.books.model.domain.User;
import com.appware_system.books.model.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    String signUp(final SignUpUser signUpUser);

    String editInfo(final EditInfo editInfo, final String email);

    String forgotPassword(final String email);

    Boolean resetChange(final String email, final String password);

    Boolean passwordChange(String email, String oldPassword, String newPassword);

    Long getIdByEmail (String email);


    User getInfo (String email);

    Boolean changeEmail (String email, String newEmail);

    UserEntity getUser (String email);


}
