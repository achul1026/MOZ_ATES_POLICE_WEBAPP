package com.moz.ates.traffic.policewebapp.tfcacdntmng.service;

import java.util.List;
import java.util.Map;

import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.common.entity.payment.MozPlPymntInfo;

public interface TrafficAcdntService {

	public List<MozTfcLwInfo> getTrafficLawsList();
	
	public List<MozPlPymntInfo> getPlacePaymentList();
	
	public void regTrafficAcdntInfo(Map<String,Object>paramMap);
}
