package com.moz.ates.traffic.policewebapp.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping(value="/login")
	public String loginPage() {
		
		return "views/login/loginPage";
	}
	
	@RequestMapping(value="logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("policeInfo");
		
		return "redirect:/";
	}
	
}
