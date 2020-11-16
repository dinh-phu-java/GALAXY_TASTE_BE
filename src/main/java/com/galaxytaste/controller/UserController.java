package com.galaxytaste.controller;

import com.galaxytaste.constant.SecurityConstant;
import com.galaxytaste.domain.Cart;
import com.galaxytaste.domain.CartItem;
import com.galaxytaste.domain.User;
import com.galaxytaste.domain.UserPrincipal;
import com.galaxytaste.exception.domain.EmailExistException;
import com.galaxytaste.exception.domain.UserNotFoundException;
import com.galaxytaste.exception.domain.UsernameExistException;
import com.galaxytaste.model.UserRegisterModel;
import com.galaxytaste.model.UserSocialLogin;
import com.galaxytaste.repository.UserRepository;
import com.galaxytaste.service.UserService;
import com.galaxytaste.utilities.JWTTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = {"/", "/user"})
public class UserController {
    private Logger LOGGER= LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterModel> register(@RequestBody UserRegisterModel user) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        this.userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User user = this.userService.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/cart/{username}")
    public ResponseEntity<Double> getCart(@PathVariable("username") String username) {
        LOGGER.info("test spring");
        User user = this.userService.findUserByUsername(username);
        Cart myCart=user.getCart();
//        myCart.getCartItems().remove(0);
        int size=myCart.getCartItems().size();

        double totalAmount= myCart.getCartItems().stream().map(item->item.getPrice()).reduce(0.0,(accumulator,element2)->accumulator+element2);
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserRegisterModel user) {
        authenticate(user.getEmail(), user.getPassword());
        User loginUser = this.userService.findUserByEmail(user.getEmail());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
    }

    @PostMapping("/check-social-email")
    public ResponseEntity<User> checkSocialEmail(@RequestBody UserSocialLogin socialUser) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        User findUser= this.userService.findUserByEmail(socialUser.getEmail());
        User returnValue=null;
        if(findUser!=null){
            returnValue=findUser;
        }else{
            String randomPassword= UUID.randomUUID().toString();
            returnValue=this.userService.register(socialUser.getFirstName(), socialUser.getName(), socialUser.getEmail(), socialUser.getEmail(), randomPassword);
            returnValue.setProfileImageUrl(socialUser.getPhotoUrl());
            this.userService.save(returnValue);
        }

        UserPrincipal userPrincipal=new UserPrincipal(returnValue);
        HttpHeaders jwtHeader=getJwtHeader(userPrincipal);
        return new ResponseEntity<>(returnValue,jwtHeader,HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers=new HttpHeaders();
        headers.add(SecurityConstant.JWT_TOKEN_HEADER,this.jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

    private void authenticate(String email, String password) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
