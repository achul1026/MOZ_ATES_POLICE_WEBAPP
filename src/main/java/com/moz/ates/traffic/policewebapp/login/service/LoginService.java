package com.moz.ates.traffic.policewebapp.login.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.police.MozPolInfo;
import com.moz.ates.traffic.common.enums.PolSttsCd;
import com.moz.ates.traffic.common.repository.police.MozPolInfoRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.policewebapp.utils.PasswordUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final MozPolInfoRepository polInfoRepository;

	public MozPolInfo getPoliceLoginInfoByPoliceLceId(String id) {
		return polInfoRepository.findOnePoliceInfoByPoliceLcenId(id);
	}

	public void registPolInfo(MozPolInfo mozPolInfo) {
		if(polInfoRepository.existsByPolLcenId(mozPolInfo.getPolLcenId())) {
			throw new CommonException(ErrorCode.DUPLICATE_ACCOUNTS);
		}
		String encodePassword = PasswordUtils.encodePassword(mozPolInfo.getAppPolPw());
		
		String polId = MozatesCommonUtils.getUuid();
		mozPolInfo.setAppPolPw(encodePassword);
		mozPolInfo.setPolId(polId);
		mozPolInfo.setAppPolId("bluedusapp");
		mozPolInfo.setCrDt(new Date());
		mozPolInfo.setAppPermission("app");
		mozPolInfo.setPolStts(PolSttsCd.WAITTING.getCode());
		
		polInfoRepository.saveMozPolInfo(mozPolInfo);
	}

	public MozPolInfo findPassword(MozPolInfo mozPolInfo) {
		String tmpPassword = MozatesCommonUtils.getUuid(8);
		String encodePassword = PasswordUtils.encodePassword(tmpPassword);
		
		MozPolInfo dbMozPolInfo = polInfoRepository.findOnePoliceInfoByPoliceLcenIdAndBrth(mozPolInfo);
		
//		if(dbMozPolInfo != null) {
//			//TODO::임시비밀번호 로 변경
//			
//		} else {
//			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
//		}
		
		mozPolInfo.setPolId("1");
		
		return mozPolInfo;
	}

	public MozPolInfo getPolInfo(String polId) {
		return polInfoRepository.findOneMozPolInfo(polId);
	}
}
