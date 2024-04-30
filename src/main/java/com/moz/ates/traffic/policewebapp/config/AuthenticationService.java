package com.moz.ates.traffic.policewebapp.config;


import com.moz.ates.traffic.common.entity.police.MozPolInfo;
import com.moz.ates.traffic.policewebapp.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
	
	private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	MozPolInfo result = loginService.getPoliceLoginInfoByPoliceLceId(username);
        PoliceAppUser user = null;
        if(result != null && result.getAppPolPw() != null){
            user = new PoliceAppUser(result);
            user.setPolId(result.getPolId());
            user.setAppPermission(result.getAppPermission());
            user.setPassword(result.getAppPolPw());
        }else{
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

}
