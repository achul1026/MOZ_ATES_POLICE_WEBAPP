package com.moz.ates.traffic.policewebapp.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.police.MozPolInfo;
import com.moz.ates.traffic.policewebapp.login.service.LoginService;

@Service
public class AuthenticationService implements UserDetailsService {
	
	private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	MozPolInfo result = loginService.getPoliceLoginInfoByPoliceId(username);
        AuthenticationEntity authenticationEntity = new AuthenticationEntity();
        if(result != null && result.getAppPolPw() != null){
            authenticationEntity.setAppPolId(result.getAppPolId());
            authenticationEntity.setAppPolPw(result.getAppPolPw());
            authenticationEntity.setAuthorities(makeGrantedAuthority(Arrays.asList(new String[] {result.getAppPermission()})));
        }else{
            throw new UsernameNotFoundException(username);
        }

        return new SecurityAccount(authenticationEntity);
    }

    private Collection<? extends GrantedAuthority> makeGrantedAuthority(List<String> roles) {
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role)));
        return list;
    }
}
