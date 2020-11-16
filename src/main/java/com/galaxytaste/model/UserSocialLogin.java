package com.galaxytaste.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserSocialLogin {
    @NotNull
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String name;
    @NotNull
    private String photoUrl;

    public UserSocialLogin(@NotNull String email, @NotNull String firstName, @NotNull String name, @NotNull String photoUrl) {
        this.email = email;
        this.firstName = firstName;
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public UserSocialLogin() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
