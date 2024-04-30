package com.moz.ates.traffic.policewebapp.myinfo.service;

import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.police.MozPolInfo;
import com.moz.ates.traffic.common.repository.police.MozPolInfoRepository;
import com.moz.ates.traffic.policewebapp.utils.LoginPoliceUtils;
import com.moz.ates.traffic.policewebapp.utils.PasswordUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyInfoService {
	
	final MozPolInfoRepository mozPolInfoRepository;
	
	/**
	  * @Method Name : getMyInfoDetail
	  * @Date : 2024. 1. 15.
	  * @Author : IK.MOON
	  * @Method Brief : 내 정보 가져오기
	  * @param model
	  * @return
	  */
	public MozPolInfo getMyInfoDetail(String polId) {
		return mozPolInfoRepository.findOneMozPolInfo(polId);
	}
		
	/**
	  * @Method Name : verfyPassword
	  * @Date : 2024. 1. 15.
	  * @Author : IK.MOON
	  * @Method Brief : 비밀번호 체크
	  * @param model
	  * @return
	  */
	public boolean verfyPassword(MozPolInfo mozPolInfo) {
		String password = mozPolInfoRepository.findPasswordByPoliceId(LoginPoliceUtils.getPolId());
		return PasswordUtils.matches(mozPolInfo.getAppPolPw(), password);
	}

	/**
	  * @Method Name : modifyPassword
	  * @Date : 2024. 1. 15.
	  * @Author : IK.MOON
	  * @Method Brief : 비밀번호 수정
	  * @param model
	  * @return
	  */
	public void modifyPassword(MozPolInfo mozPolInfo) {
		String newPassword = PasswordUtils.encodePassword(mozPolInfo.getNewPw());
        
		MozPolInfo updateMozPolInfo = new MozPolInfo();
		updateMozPolInfo.setPolId(LoginPoliceUtils.getPolId());
		updateMozPolInfo.setAppPolPw(newPassword);
		
		mozPolInfoRepository.updateAppPolPwByPolId(updateMozPolInfo);
	}

	/**
	  * @Method Name : modifyMyProfile
	  * @Date : 2024. 1. 15.
	  * @Author : IK.MOON
	  * @Method Brief : 회원정보 수정
	  * @param model
	  * @return
	  */
	public void modifyMyProfile(MozPolInfo mozPolInfo) {
		mozPolInfoRepository.updateMozPolInfoByPoliceApp(mozPolInfo);
	}
}
