package com.moz.ates.traffic.policewebapp.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.moz.ates.traffic.common.entity.police.MozPolInfo;
import com.moz.ates.traffic.policewebapp.login.service.LoginService;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        //성공시 실패한 세션 아이디 정리
        authenticationAttributes(request);
        HttpSession session = request.getSession();
        MozPolInfo polInfo = new MozPolInfo();
        
        polInfo.setAppPolId(authentication.getName());
        MozPolInfo policeInfo = loginService.getPoliceLoginInfo(polInfo);
        session.setAttribute("policeInfo", policeInfo);
        // 로그인 성공시 보내는 주소
        String redirectUrl = "/tfcenfmng/trafficEnfceRegPage";
        getRedirectStrategy().sendRedirect(request,response,redirectUrl);

    }

    protected void authenticationAttributes(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session==null) return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
