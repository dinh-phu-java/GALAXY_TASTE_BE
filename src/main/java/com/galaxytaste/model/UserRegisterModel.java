package com.galaxytaste.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterModel {
    @NotNull(message="First Name should not be null")
    private String firstName;
    @NotNull(message="First Name should not be null")
    private String lastName;
    @NotNull(message="First Name should not be null")
    private String username;
    @NotNull(message="First Name should not be null")
    @Email(message="Email format is not correct")
    private String email;
    @NotNull(message="First Name should not be null")
    private String password;

    public UserRegisterModel() {
    }

    public UserRegisterModel(@NotNull(message = "First Name should not be null") String firstName, @NotNull(message = "First Name should not be null") String lastName, @NotNull(message = "First Name should not be null") String username, @NotNull(message = "First Name should not be null") @Email(message = "Email format is not correct") String email, @NotNull(message = "First Name should not be null") String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
