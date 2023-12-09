package com.moz.ates.traffic.policewebapp.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class AuthenticationEntity {
    private String appPolId;
    private String appPolPw;
    private Collection<? extends GrantedAuthority> authorities;
}
