package com.moz.ates.traffic.policewebapp.tfcenfmng.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.driver.MozVioInfo;
import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfHst;
import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.common.entity.payment.MozFinePymntInfo;
import com.moz.ates.traffic.common.entity.payment.MozPlPymntInfo;
import com.moz.ates.traffic.common.repository.driver.MozVioInfoRepository;
import com.moz.ates.traffic.common.repository.enforcement.MozTfcEnfHstRepository;
import com.moz.ates.traffic.common.repository.enforcement.MozTfcEnfMasterRepository;
import com.moz.ates.traffic.common.repository.law.MozTfcLwInfoRepository;
import com.moz.ates.traffic.common.repository.payment.MozFinePymntInfoRepository;
import com.moz.ates.traffic.common.repository.payment.MozPlPymntInfoRepository;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.policewebapp.tfcenfmng.service.TrafficEnfceService;

@Service
public class TrafficEnfceServiceImpl implements TrafficEnfceService{
	
	@Autowired
    MozTfcLwInfoRepository tfcLwInfoRepository;
	
	@Autowired
	MozPlPymntInfoRepository plPymntInfoRepository;
	
	@Autowired
	MozVioInfoRepository vioInfoRepository;
	
	@Autowired
	MozTfcEnfMasterRepository tfcEnfMasterRepository;
	
	@Autowired
	MozTfcEnfHstRepository tfcEnfHstRepository;
	
	@Autowired
	MozFinePymntInfoRepository finePymntInfoRepository;
	
	@Override
	public List<MozTfcLwInfo> getTrafficLawsList() {
		MozTfcLwInfo tfcLwInfo = new MozTfcLwInfo();
		return tfcLwInfoRepository.selectTrafficLawsList(tfcLwInfo);
	}
	
	@Override
	public List<MozPlPymntInfo> getPlacePaymentList() {
		MozPlPymntInfo plPymntInfo = new MozPlPymntInfo();
		return plPymntInfoRepository.selectPlacePaymentList(plPymntInfo); 
	}
	
	@Override
	public void regTrafficEnfceInfo(Map<String, Object> paramMap) {
		
		//vio_info_tb Insert Map
		MozVioInfo vioInfo = new MozVioInfo();
		String vioId = MozatesCommonUtils.getUuid();
		String dvrLcenId 	= paramMap.get("dvrLcenId") 	!= null ? String.valueOf(paramMap.get("dvrLcenId")) 	: "";
		String vioNm 		= paramMap.get("vioNm") 		!= null ? String.valueOf(paramMap.get("vioNm")) 		: "";
		String vioBrth 		= paramMap.get("vioBrth") 		!= null ? String.valueOf(paramMap.get("vioBrth")) 		: "";
		String vioAddr 		= paramMap.get("vioAddr") 		!= null ? String.valueOf(paramMap.get("vioAddr")) 		: "";
		String vioPno 		= paramMap.get("vioPno") 		!= null ? String.valueOf(paramMap.get("vioPno")) 		: "";
		String vioEmail 	= paramMap.get("vioEmail") 		!= null ? String.valueOf(paramMap.get("vioEmail")) 		: "";
		String dvrLcenTy 	= paramMap.get("dvrLcenTy") 	!= null ? String.valueOf(paramMap.get("dvrLcenTy")) 	: "";
		String crtr 		= paramMap.get("polNm") 		!= null ? String.valueOf(paramMap.get("polNm")) 		: "";
		
		vioInfo.setVioId(vioId);
		vioInfo.setDvrLcenId(dvrLcenId);
		vioInfo.setVioNm(vioNm);
		vioInfo.setVioBrth(vioBrth);
		vioInfo.setVioAddr(vioAddr);
		vioInfo.setVioPno(vioPno);
		vioInfo.setVioEmail(vioEmail);
		vioInfo.setDvrLcenTy(dvrLcenTy);
		vioInfo.setCrtr(crtr);
		vioInfoRepository.insertVioInfo(vioInfo);
		
		//vio_info_tb Insert Map
		MozTfcEnfMaster tfcEnfMaster = new MozTfcEnfMaster();
		String tfcEnfId = MozatesCommonUtils.getTfcEnfId();
		String tfcLawId 	= paramMap.get("tfcLawId") 		!= null ? String.valueOf(paramMap.get("tfcLawId")) 		: "";
		String roadLn 		= paramMap.get("roadLn") 		!= null ? String.valueOf(paramMap.get("roadLn")) 		: "";
		String spdLmt 		= paramMap.get("spdLmt") 		!= null ? String.valueOf(paramMap.get("spdLmt")) 		: "";
		String vioSpd 		= paramMap.get("vioSpd") 		!= null ? String.valueOf(paramMap.get("vioSpd")) 		: "";
		String overSpd 		= paramMap.get("overSpd") 		!= null ? String.valueOf(paramMap.get("overSpd")) 		: "";
		String lat 			= paramMap.get("lat") 			!= null ? String.valueOf(paramMap.get("lat")) 			: "";
		String lng 			= paramMap.get("lng") 			!= null ? String.valueOf(paramMap.get("lng")) 			: "";
		String polId 		= paramMap.get("polId") 		!= null ? String.valueOf(paramMap.get("polId")) 		: "";
		String roadAddr 	= paramMap.get("roadAddr") 		!= null ? String.valueOf(paramMap.get("roadAddr")) 		: "";
		String vhRegNo 		= paramMap.get("vhRegNo") 		!= null ? String.valueOf(paramMap.get("vhRegNo")) 		: "";
		String vhTy 		= paramMap.get("vhTy") 			!= null ? String.valueOf(paramMap.get("vhTy")) 			: "";
		
		tfcEnfMaster.setTfcEnfId(tfcEnfId);
		tfcEnfMaster.setTfcLawId(tfcLawId);
		tfcEnfMaster.setVioId(vioId);
		tfcEnfMaster.setPolId(polId);
		tfcEnfMaster.setRoadAddr(roadAddr);
		tfcEnfMaster.setRoadLn(roadLn);
		tfcEnfMaster.setVhRegNo(vhRegNo);
		tfcEnfMaster.setVhTy(vhTy);
		tfcEnfMaster.setSpdLmt(spdLmt);
		tfcEnfMaster.setVioSpd(vioSpd);
		tfcEnfMaster.setOverSpd(overSpd);
		tfcEnfMaster.setLng(lng);
		tfcEnfMaster.setLat(lat);
		tfcEnfMaster.setLastTfcEnfProcCd("TEP001");		//last_tfc_enf_proc_cd TEP001 = > 등록
		tfcEnfMaster.setCrtr(crtr);
		tfcEnfMasterRepository.insertTfcEnfInfo(tfcEnfMaster);
		
		//tfc_enf_hst_tb Insert Map
		MozTfcEnfHst tfcEnfHst = new MozTfcEnfHst();
		String enfHstId = MozatesCommonUtils.getUuid();
		tfcEnfHst.setHstId(enfHstId); 
		tfcEnfHst.setTfcEnfId(tfcEnfId);
		tfcEnfHst.setTfcEnfPrcCd("TEP001");	//last_tfc_enf_proc_cd TEP001 = > 등록
		tfcEnfHst.setCrtr(crtr);
		tfcEnfHstRepository.insertTfcEnfHstInfo(tfcEnfHst);
		
		//fine_pymnt_info_tb Insert Map
		MozFinePymntInfo finePymntInfo = new MozFinePymntInfo();
		String pymntId = MozatesCommonUtils.getUuid();
		String placePymntId 	= paramMap.get("placePymntId") 		!= null ? String.valueOf(paramMap.get("placePymntId")) 		: "";
		String totalPrice 		= paramMap.get("totalPrice") 		!= null ? String.valueOf(paramMap.get("totalPrice")) 		: "";
		String pymntPrice 		= paramMap.get("pymntPrice") 		!= null ? String.valueOf(paramMap.get("pymntPrice")) 		: "";
		String pymntOprtr 		= paramMap.get("pymntOprtr") 		!= null ? String.valueOf(paramMap.get("pymntOprtr")) 		: "";
		
		//PYMNT_DT, PYMNT_DDLN 납부 날짜 / 기한 확인필요
		finePymntInfo.setPymntId(pymntId);
		finePymntInfo.setTfcEnfId(tfcEnfId);
		finePymntInfo.setPayerNm(vioNm);
		finePymntInfo.setPlacePymntId(placePymntId);
		finePymntInfo.setTotalPrice(totalPrice);
		finePymntInfo.setPymntPrice(pymntPrice);
		finePymntInfo.setPymntOprtr(pymntOprtr);
		finePymntInfo.setPymntStts("N");
		finePymntInfo.setCrtr(crtr);
		finePymntInfoRepository.insertFinePaymentInfo(finePymntInfo);
		
	}
	
}
