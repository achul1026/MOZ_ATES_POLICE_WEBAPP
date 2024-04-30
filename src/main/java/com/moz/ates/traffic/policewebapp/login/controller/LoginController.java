package com.moz.ates.traffic.policewebapp.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moz.ates.traffic.common.entity.common.CommonResponse;
import com.moz.ates.traffic.common.entity.police.MozPolInfo;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.policewebapp.config.constants.PoliceAppConstants;
import com.moz.ates.traffic.policewebapp.login.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	LoginService loginService;


	/**
	 * Login page
	 * @return presentation page
	 */
	@GetMapping(value="/login")
	public String loginPage(HttpSession session) {
		if(session != null) {
			session.invalidate();
		}
		return "views/login/loginPage";
	}

	/**
	 * Logout page
	 * @param session Current session
	 * @return Redirect
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(PoliceAppConstants.POLICE_SESSION_KEY);
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping(value="/passWordFind")
	public String passWordFind() {
		return "views/login/passWordFind";
	}
	
	@PostMapping(value="/passWordFindAjax")
	@ResponseBody
	public CommonResponse<?> passWordFindAjax(MozPolInfo mozPolInfo) {
		if(
			MozatesCommonUtils.isNull(mozPolInfo.getPolLcenId()) ||
			mozPolInfo.getBrth() == null
		) {
			throw new CommonException(ErrorCode.INVALID_PARAMETER);
		}
		MozPolInfo data = null;
		try {
			data = loginService.findPassword(mozPolInfo);
		} catch (CommonException e){
			e.printStackTrace();
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}
		return CommonResponse.ResponseSuccess(HttpStatus.OK, "Registration Success", null, data);
	}
	
	@GetMapping(value="/passWordComplete")
	public String passWordComplete(Model model, @RequestParam(name="polId") String polId) {
		model.addAttribute("mozPlInfo", loginService.getPolInfo(polId));
		return "views/login/passWordComplete";
	}
	
	@GetMapping(value="/join")
	public String join() {
		
		return "views/login/join";
	}
	@GetMapping(value="/policeFind")
	public String policeFind() {
		
		return "views/login/policeFind";
	}
	
	@PostMapping(value="/joinUsAjax")
	public String joinUsAjax(Model model, MozPolInfo mozPolInfo) {
		if(
			MozatesCommonUtils.isNull(mozPolInfo.getPolLcenId()) ||
			MozatesCommonUtils.isNull(mozPolInfo.getPolNm())	||
			MozatesCommonUtils.isNull(mozPolInfo.getAppPolPw())	||
			MozatesCommonUtils.isNull(mozPolInfo.getPhone())	||
			mozPolInfo.getPolLcenDt() == null	||
			mozPolInfo.getBrth() == null
		) {
			throw new CommonException(ErrorCode.INVALID_PARAMETER);
		}
		
		try {
			loginService.registPolInfo(mozPolInfo);
		} catch (CommonException e){
			throw new CommonException(ErrorCode.DATA_INSERT_FAIL);
		}
		return "views/login/joinComplete";
	}
	
	@RequestMapping(value="/joinComplete")
	public String joinComplete() {
		
		return "views/login/joinComplete";
	}
}
