package com.galaxytaste.service.impl;

import com.galaxytaste.domain.Cart;
import com.galaxytaste.domain.User;
import com.galaxytaste.domain.UserPrincipal;
import com.galaxytaste.enumeration.Role;
import com.galaxytaste.exception.domain.EmailExistException;
import com.galaxytaste.exception.domain.EmailNotFoundException;
import com.galaxytaste.exception.domain.UserNotFoundException;
import com.galaxytaste.exception.domain.UsernameExistException;
import com.galaxytaste.repository.UserRepository;
import com.galaxytaste.service.EmailService;
import com.galaxytaste.service.LoginAttemptService;
import com.galaxytaste.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.galaxytaste.constant.FileConstant.*;
import static com.galaxytaste.constant.UserImplConstant.*;


@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    public static final String DEFAULT_PROFILE_IMAGE = "https://dinhphuappv1.s3-ap-southeast-1.amazonaws.com/default-user-icon-4.jpg";
    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private LoginAttemptService loginAttemptService;
    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findUserByUsername(username);
        if (user == null) {
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {
            validateLoginAttempt(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            return userPrincipal;
        }
    }

    private void validateLoginAttempt(User user) {
    }


    @Override
    public User register(String firstName, String lastName, String username, String email, String password) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);
        User user = new User();
        Cart cart=new Cart();

        String encodePassword = encodePassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodePassword);
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Role.ROLE_USER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileUrl());
        cart.setUser(user);
        user.setCart(cart);
        this.userRepository.save(user);
        LOGGER.info("New user was created with password: " + password);
        return user;
    }


    @Override
    public User addNewUser(String firstName, String lastName, String username, String email, String role, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        return null;
    }

    @Override
    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);

        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);

        currentUser.setRole(Role.ROLE_USER.name());
        currentUser.setAuthorities(Role.ROLE_USER.getAuthorities());
        currentUser.setProfileImageUrl(getTemporaryProfileUrl());
        this.userRepository.save(currentUser);
        LOGGER.info("New user was created with username: " + currentUsername);
        return currentUser;
    }

    @Override
    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException {

    }

    @Override
    public User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        User user = this.userRepository.findUserByUsername(username);
        saveProfileImage(user, profileImage);
        return user;
    }

    private void saveProfileImage(User user, MultipartFile profileImage) throws IOException {
        if (profileImage != null) {
            Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION));
            user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
            this.userRepository.save(user);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(USER_IMAGE_PATH +
                        username +
                        FORWARD_SLASH +
                        username +
                        DOT +
                        JPG_EXTENSION).toUriString();
    }


    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }


    private String getTemporaryProfileUrl() {
        return DEFAULT_PROFILE_IMAGE;
    }

    private String encodePassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User userByNewUserName = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if (StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if (currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }

            if (userByNewUserName != null && !currentUser.getId().equals(userByNewUserName.getId())) {
                throw new UsernameExistException(USERNAME_ALREADY_EXIST);
            }

            if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException(EMAIL_ALREADY_EXIST);
            }
            return currentUser;
        } else {
            if (userByNewUserName != null) {
                throw new UsernameExistException(USERNAME_ALREADY_EXIST);
            }

            if (userByNewEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXIST);
            }
            return null;
        }
    }
}
