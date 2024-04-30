package com.moz.ates.traffic.policewebapp.myinfo.controller;

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
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.policewebapp.config.PoliceAppUser;
import com.moz.ates.traffic.policewebapp.myinfo.service.MyInfoService;
import com.moz.ates.traffic.policewebapp.utils.LoginPoliceUtils;

@Controller
@RequestMapping(value = "/myinfo")
public class MyInfoController {
	
	@Autowired
	MyInfoService myInfoService;
	
	/**
	  * @Method Name : getMngList
	  * @Date : 2024. 1. 15.
	  * @Author : IK.MOON
	  * @Method Brief : 설정 페이지 이동
	  * @param model
	  * @return
	  */
	@GetMapping("/list")
	String getMngList(Model model) {
		return "views/myinfo/myInfoList";
	}
	
	/**
	  * @Method Name : getMyInfoDetail
	  * @Date : 2024. 1. 15.
	  * @Author : KY.LEE
	  * @Method Brief : 내 정보 변경 페이지 이동
	  * @param model
	  * @return
	  */
	@GetMapping("/detail")
	String getMyInfoDetail(Model model) {
		model.addAttribute("myInfoDetail", myInfoService.getMyInfoDetail(LoginPoliceUtils.getPolId()));
		return "views/myinfo/myInfoDetail";
	}

	/**
	 * @Method Name : getMyInfoPassConfirm
	 * @Date : 2024. 3. 21.
	 * @Author : IK.MOON
	 * @Method Brief : 현재 비밀번호 입력 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/passConfirm")
	String getMyInfoPassConfirm(Model model , @RequestParam(name="type") String type) {
		HttpSession session = LoginPoliceUtils.getSession();
		session.removeAttribute("isPwChk");
		
		model.addAttribute("type", type);
		return "views/myinfo/myInfoPassConfirm";
	}

	/**
	 * @Method Name : verfyPassword
	 * @Date : 2024. 3. 21.
	 * @Author : IK.MOON
	 * @Method Brief : 현재 비밀번호 찾기
	 * @param model
	 * @return
	 */
	@PostMapping("/password/verfy")
	public @ResponseBody CommonResponse<?> verfyPassword(Model model , MozPolInfo mozPolInfo) {
		if(MozatesCommonUtils.isNull(mozPolInfo.getAppPolPw())) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST,"Parameter Is Null");
		}
		
		if(!myInfoService.verfyPassword(mozPolInfo)) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST,"Password Mismatch");
		}
		HttpSession session = LoginPoliceUtils.getSession();
		
		session.setAttribute("isPwChk", true);
		
        return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK,"비밀번호 확인완료");
	}
	
	/**
	 * @Method Name : myInfoPassModify
	 * @Date : 2024. 3. 21.
	 * @Author : IK.MOON
	 * @Method Brief : 비밀번호 변경 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/passModify/detail")
	String myInfoPassModify(Model model) {
		HttpSession session = LoginPoliceUtils.getSession();
		
	    boolean isPwChk = session.getAttribute("isPwChk") != null ? (boolean) session.getAttribute("isPwChk") : false;

	    model.addAttribute("isPwChk" , isPwChk);
		
	    return "views/myinfo/myInfoPassModify";
	}
	
	/**
	 * @Method Name : myInfoPassModify
	 * @Date : 2024. 3. 21.
	 * @Author : IK.MOON
	 * @Method Brief : 비밀번호 변경 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/polInfo/modify")
	String polInfoModify(Model model) {
		HttpSession session = LoginPoliceUtils.getSession();
		
		boolean isPwChk = session.getAttribute("isPwChk") != null ? (boolean) session.getAttribute("isPwChk") : false;

		model.addAttribute("isPwChk" , isPwChk);
		model.addAttribute("myInfoDetail", myInfoService.getMyInfoDetail(LoginPoliceUtils.getPolId()));
		
		return "views/myinfo/myInfoModify";
	}
	
	/**
	 * @Method Name : modifyPassword
	 * @Date : 2024. 3. 21.
	 * @Author : IK.MOON
	 * @Method Brief : 비밀번호 수정
	 * @param model
	 * @return
	 */
	@PostMapping("/modify")
	public @ResponseBody CommonResponse<?> myInfoModify(Model model , MozPolInfo mozPolInfo) {
		if(MozatesCommonUtils.isNull(mozPolInfo.getPolNm()) || MozatesCommonUtils.isNull(mozPolInfo.getPhone())) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST,"Parameter Is Null");
		}
		
		myInfoService.modifyMyProfile(mozPolInfo);
		
		HttpSession session = LoginPoliceUtils.getSession();
		
		session.removeAttribute("isPwChk");
		
		return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK,"Modify Success");
	}
	
	/**
	 * @Method Name : modifyPassword
	 * @Date : 2024. 3. 21.
	 * @Author : IK.MOON
	 * @Method Brief : 비밀번호 수정
	 * @param model
	 * @return
	 */
	@PostMapping("/password/modify")
	public @ResponseBody CommonResponse<?> modifyPassword(Model model , MozPolInfo mozPolInfo) {
		if(MozatesCommonUtils.isNull(mozPolInfo.getNewPw()) || MozatesCommonUtils.isNull(mozPolInfo.getNewPwChk())) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST,"Parameter Is Null");
		}
		
		if(!mozPolInfo.getNewPw().equals(mozPolInfo.getNewPwChk())) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST,"Password does not match");
		}
		
		myInfoService.modifyPassword(mozPolInfo);
		
		HttpSession session = LoginPoliceUtils.getSession();
		
		PoliceAppUser loginUser =  LoginPoliceUtils.getPoliceAppUser();
		
		session.removeAttribute("isPwChk");
		
		return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK,"Modify Success");
	}
	
	@GetMapping("/myInfoCheckSearch/detail")
	String myInfoCheckSearch(Model model) {
		return "views/myinfo/myInfoCheckSearch";
	}
	
}
