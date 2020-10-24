package com.galaxytaste.controller;

import com.galaxytaste.constant.SecurityConstant;
import com.galaxytaste.domain.Cart;
import com.galaxytaste.domain.CartItem;
import com.galaxytaste.domain.User;
import com.galaxytaste.domain.UserPrincipal;
import com.galaxytaste.exception.domain.EmailExistException;
import com.galaxytaste.exception.domain.UserNotFoundException;
import com.galaxytaste.exception.domain.UsernameExistException;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = {"/", "/user"})
public class UserController {
    private Logger LOGGER= LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
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

        int size=myCart.getCartItems().size();

        double totalAmount= myCart.getCartItems().stream().map(item->item.getPrice()).reduce(0.0,(accumulator,element2)->accumulator+element2);
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = this.userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers=new HttpHeaders();
        headers.add(SecurityConstant.JWT_TOKEN_HEADER,this.jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

    private void authenticate(String username, String password) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
