package com.galaxytaste.service;

import com.galaxytaste.domain.User;
import com.galaxytaste.exception.domain.EmailExistException;
import com.galaxytaste.exception.domain.EmailNotFoundException;
import com.galaxytaste.exception.domain.UserNotFoundException;
import com.galaxytaste.exception.domain.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface UserService {
    User register(String firstName,String lastName,String username,String email,String password) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User addNewUser(String firstName, String lastName, String username, String email, String role, MultipartFile profileImage ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

    User updateUser(String currentUsername,String newFirstName, String newLastName, String newUsername, String newEmail, String role, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

    void deleteUser(long id);

    void resetPassword(String email) throws EmailNotFoundException;

    User updateProfileImage(String username,MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

    User save(User user);
}
