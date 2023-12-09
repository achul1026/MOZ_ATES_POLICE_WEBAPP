package com.moz.ates.traffic.policewebapp.tfcacdntmng.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntChgHst;
import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntMaster;
import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntPnrInfo;
import com.moz.ates.traffic.common.entity.driver.MozVioInfo;
import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfHst;
import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.common.entity.payment.MozFinePymntInfo;
import com.moz.ates.traffic.common.entity.payment.MozPlPymntInfo;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntChgHstRepository;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntMasterRepository;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntPnrInfoRepository;
import com.moz.ates.traffic.common.repository.driver.MozVioInfoRepository;
import com.moz.ates.traffic.common.repository.enforcement.MozTfcEnfHstRepository;
import com.moz.ates.traffic.common.repository.enforcement.MozTfcEnfMasterRepository;
import com.moz.ates.traffic.common.repository.law.MozTfcLwInfoRepository;
import com.moz.ates.traffic.common.repository.payment.MozFinePymntInfoRepository;
import com.moz.ates.traffic.common.repository.payment.MozPlPymntInfoRepository;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.policewebapp.tfcacdntmng.service.TrafficAcdntService;

@Service
public class TrafficAcdntServiceImpl implements TrafficAcdntService{
	
	@Autowired
    MozTfcLwInfoRepository tfcLwInfoRepository;
	
	@Autowired
	MozPlPymntInfoRepository plPymntInfoRepository;
	
	@Autowired
	MozVioInfoRepository vioInfoRepository;
	
	@Autowired
	MozTfcAcdntMasterRepository tfcAcdntMasterRepository;
	
	@Autowired
	MozTfcAcdntPnrInfoRepository tfcAcdntPnrInfoRepository;
	
	@Autowired
	MozTfcEnfMasterRepository tfcEnfMasterRepository;
	
	@Autowired
	MozFinePymntInfoRepository finePymntInfoRepository;
	
	@Autowired
	MozTfcAcdntChgHstRepository tfcAcdntChgHstRepository;
	
	@Autowired
	MozTfcEnfHstRepository tfcEnfHstRepository;

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
	public void regTrafficAcdntInfo(Map<String, Object> paramMap) {
		//사고 등록 정보 SIZE
		int tfcAcdntInfoSize = Integer.parseInt((String)paramMap.get("trafficAcdntSize"));
		
		//교통사고차량 그룹아이디
		String acdntVhGrpId = MozatesCommonUtils.getUuid();
		
		for(int i = 1; i < tfcAcdntInfoSize+1; i++) {
			
			//vio_info_tb Insert Map
			MozVioInfo vioInfo = new MozVioInfo();
			String vioId = MozatesCommonUtils.getUuid();
			String dvrLcenId 	= paramMap.get("dvrLcenId"+i) 	!= null ? String.valueOf(paramMap.get("dvrLcenId"+i)) 	: "";
			String vioNm 		= paramMap.get("vioNm"+i) 		!= null ? String.valueOf(paramMap.get("vioNm"+i)) 		: "";
			String vioBrth 		= paramMap.get("vioBrth"+i) 	!= null ? String.valueOf(paramMap.get("vioBrth"+i)) 	: "";
			String vioAddr 		= paramMap.get("vioAddr"+i) 	!= null ? String.valueOf(paramMap.get("vioAddr"+i)) 	: "";
			String vioPno 		= paramMap.get("vioPno"+i) 		!= null ? String.valueOf(paramMap.get("vioPno"+i)) 		: "";
			String vioEmail 	= paramMap.get("vioEmail"+i) 	!= null ? String.valueOf(paramMap.get("vioEmail"+i)) 	: "";
			String dvrLcenTy 	= paramMap.get("dvrLcenTy"+i) 	!= null ? String.valueOf(paramMap.get("dvrLcenTy"+i)) 	: "";
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
			
			
			//tfc_acdnt_master_tb Insert Map
			MozTfcAcdntMaster tfcAcdntMaster = new MozTfcAcdntMaster();
			String tfcAcdntId 	=  MozatesCommonUtils.getUuid();
			String vhRegNo 		= paramMap.get("vhRegNo"+i) 	!= null ? String.valueOf(paramMap.get("vhRegNo"+i)) 	: "";
			String vhTy 		= paramMap.get("vhTy"+i) 		!= null ? String.valueOf(paramMap.get("vhTy"+i)) 		: "";
			String lat 			= paramMap.get("lat"+i) 		!= null ? String.valueOf(paramMap.get("lat"+i)) 		: "";
			String lng 			= paramMap.get("lng"+i) 		!= null ? String.valueOf(paramMap.get("lng"+i)) 		: "";
			String roadAddr 	= paramMap.get("roadAddr"+i) 	!= null ? String.valueOf(paramMap.get("roadAddr"+i)) 	: "";
			String polId 		= paramMap.get("polId") 		!= null ? String.valueOf(paramMap.get("polId")) 		: "";
			
			tfcAcdntMaster.setTfcAcdntId(tfcAcdntId);
			tfcAcdntMaster.setAcdntVhGrpId(acdntVhGrpId);
			tfcAcdntMaster.setVhRegNo(vhRegNo);
			tfcAcdntMaster.setVhTy(vhTy);
			tfcAcdntMaster.setLat(lat);
			tfcAcdntMaster.setLng(lng);
			tfcAcdntMaster.setRoadAddr(roadAddr);
			tfcAcdntMaster.setCrtr(crtr);
			tfcAcdntMaster.setPolId(polId);
			tfcAcdntMaster.setLastAcdntPrcCd("TAP001"); //TAP001 = > 교통 사고 등록
			tfcAcdntMasterRepository.insertAcdnt(tfcAcdntMaster);
			
			// 탑승자 정보 SIZE
			int pnrSize = Integer.parseInt((String)paramMap.get("pnrSize"+i));
			
			if( pnrSize > 0 ) {
				for(int j = 1; j < pnrSize+1; j++) {
					//tfc_acdnt_master_tb Insert Map
					MozTfcAcdntPnrInfo tfcAcdntPnrInfo = new MozTfcAcdntPnrInfo();
					String dvrId 		= MozatesCommonUtils.getUuid();
					String pnrNm 		= paramMap.get("pnrNm"+i+"_"+j) 		!= null ? String.valueOf(paramMap.get("pnrNm"+i+"_"+j)) 		: "";
					String pnrBrth		= paramMap.get("pnrBrth"+i+"_"+j) 		!= null ? String.valueOf(paramMap.get("pnrBrth"+i+"_"+j)) 		: "";
					String pnrAddr		= paramMap.get("pnrAddr"+i+"_"+j) 		!= null ? String.valueOf(paramMap.get("pnrAddr"+i+"_"+j)) 		: "";
					String pnrPno 		= paramMap.get("pnrPno"+i+"_"+j) 		!= null ? String.valueOf(paramMap.get("pnrPno"+i+"_"+j)) 		: "";
					String pnrEmail 	= paramMap.get("pnrEmail"+i+"_"+j) 		!= null ? String.valueOf(paramMap.get("pnrEmail"+i+"_"+j)) 		: "";
					String dvYn 		= paramMap.get("dvYn"+i+"_"+j) 			!= null ? String.valueOf(paramMap.get("dvYn"+i+"_"+j)) 			: "";
					String pnrDvrLcenTy	= paramMap.get("dvrLcenTy"+i+"_"+j) 	!= null ? String.valueOf(paramMap.get("dvrLcenTy"+i+"_"+j)) 	: "";
					
					tfcAcdntPnrInfo.setDvrId(dvrId);
					tfcAcdntPnrInfo.setTfcAcdntId(tfcAcdntId);
					tfcAcdntPnrInfo.setDvrLcenId(dvrLcenId);
					tfcAcdntPnrInfo.setPnrNm(pnrNm);
					tfcAcdntPnrInfo.setPnrBrth(pnrBrth);
					tfcAcdntPnrInfo.setPnrAddr(pnrAddr);
					tfcAcdntPnrInfo.setPnrPno(pnrPno);
					tfcAcdntPnrInfo.setPnrEmail(pnrEmail);
					tfcAcdntPnrInfo.setDvYn(dvYn);
					tfcAcdntPnrInfo.setDvrLcenTy(pnrDvrLcenTy);
					tfcAcdntPnrInfo.setCrtr(crtr);
					tfcAcdntPnrInfoRepository.insertPnrAcdntInfo(tfcAcdntPnrInfo);
				}
			}
						
			//vio_info_tb Insert Map
			MozTfcEnfMaster tfcEnfMaster = new MozTfcEnfMaster();
			String tfcEnfId = MozatesCommonUtils.getTfcEnfId();
			String tfcLawId 	= paramMap.get("tfcLawId"+i) 	!= null ? String.valueOf(paramMap.get("tfcLawId"+i)) 	: "";
			String roadLn 		= paramMap.get("roadLn"+i) 		!= null ? String.valueOf(paramMap.get("roadLn"+i)) 		: "";
			String spdLmt 		= paramMap.get("spdLmt"+i) 		!= null ? String.valueOf(paramMap.get("spdLmt"+i)) 		: "";
			String vioSpd 		= paramMap.get("vioSpd"+i) 		!= null ? String.valueOf(paramMap.get("vioSpd"+i)) 		: "";
			String overSpd 		= paramMap.get("overSpd"+i) 	!= null ? String.valueOf(paramMap.get("overSpd"+i)) 	: "";
			
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
			
			//fine_pymnt_info_tb Insert Map
			MozFinePymntInfo finePymntInfo = new MozFinePymntInfo();
			String pymntId = MozatesCommonUtils.getUuid();
			String placePymntId 	= paramMap.get("placePymntId"+i) 	!= null ? String.valueOf(paramMap.get("placePymntId"+i)) 	: "";
			String totalPrice 		= paramMap.get("totalPrice"+i) 		!= null ? String.valueOf(paramMap.get("totalPrice"+i)) 		: "";
			String pymntPrice 		= paramMap.get("pymntPrice"+i) 		!= null ? String.valueOf(paramMap.get("pymntPrice"+i)) 		: "";
			String pymntOprtr 		= paramMap.get("pymntOprtr"+i) 		!= null ? String.valueOf(paramMap.get("pymntOprtr"+i)) 		: "";
			
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
			
			//tfc_acdnt_chg_hst_tb Insert Map
			MozTfcAcdntChgHst tfcAcdntChgHst = new MozTfcAcdntChgHst();
			String hstId = MozatesCommonUtils.getUuid();
			tfcAcdntChgHst.setHstId(hstId);
			tfcAcdntChgHst.setTfcAcdntId(tfcAcdntId);
			tfcAcdntChgHst.setAcdntPrcCd("TAP001"); //TAP001 = > 교통 사고 등록
			tfcAcdntChgHst.setCrtr(crtr);
			tfcAcdntChgHstRepository.insertTfcAcdntHstInfo(tfcAcdntChgHst);
			
			
			//tfc_enf_hst_tb Insert Map
			MozTfcEnfHst tfcEnfHst = new MozTfcEnfHst();
			String enfHstId = MozatesCommonUtils.getUuid();
			tfcEnfHst.setHstId(enfHstId); 
			tfcEnfHst.setTfcEnfId(tfcEnfId);
			tfcEnfHst.setTfcEnfPrcCd("TEP001");	//last_tfc_enf_proc_cd TEP001 = > 등록
			tfcEnfHst.setCrtr(crtr);
			tfcEnfHstRepository.insertTfcEnfHstInfo(tfcEnfHst);
		}
	}
	
}
