package com.galaxytaste.enumeration;

import com.galaxytaste.constant.Authority;

import static com.galaxytaste.constant.Authority.*;

public enum Role {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

    private String[] authorities;
    Role(String[] authorities){
        this.authorities=authorities;
    }
    public String[] getAuthorities(){
        return this.authorities;
    }
}
