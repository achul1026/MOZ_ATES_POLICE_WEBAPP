package com.moz.ates.traffic.policewebapp.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.police.MozPolInfo;
import com.moz.ates.traffic.common.repository.police.MozPolInfoRepository;
import com.moz.ates.traffic.policewebapp.login.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private MozPolInfoRepository polInfoRepository;
	
	@Override
	public MozPolInfo getPoliceLoginInfo(MozPolInfo polInfo) {
		return polInfoRepository.selectPoliceInfo(polInfo);
	}
	
	@Override
	public MozPolInfo getPoliceLoginInfoByPoliceId(String id) {
		return polInfoRepository.selectPoliceInfoByPoliceId(id);
	}

}
