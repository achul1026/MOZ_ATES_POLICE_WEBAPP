package com.moz.ates.traffic.policewebapp.config;


import org.springframework.security.core.userdetails.User;


public class SecurityAccount extends User {


    public SecurityAccount(AuthenticationEntity authenticationEntity) {
        super(authenticationEntity.getAppPolId(), authenticationEntity.getAppPolPw(), authenticationEntity.getAuthorities());
    }
}
