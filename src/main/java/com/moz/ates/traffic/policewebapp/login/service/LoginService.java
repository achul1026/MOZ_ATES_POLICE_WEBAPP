package com.moz.ates.traffic.policewebapp.login.service;

import com.moz.ates.traffic.common.entity.police.MozPolInfo;

public interface LoginService {
	

	public MozPolInfo getPoliceLoginInfo(MozPolInfo polInfo);
	
	public MozPolInfo getPoliceLoginInfoByPoliceId(String id);
}
