package com.moz.ates.traffic.policewebapp.tfcenfmng.service;

import java.util.List;
import java.util.Map;

import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.common.entity.payment.MozPlPymntInfo;

public interface TrafficEnfceService {

	public List<MozTfcLwInfo> getTrafficLawsList();
	
	public List<MozPlPymntInfo> getPlacePaymentList();
	
	public void regTrafficEnfceInfo(Map<String,Object>paramMap);
}
