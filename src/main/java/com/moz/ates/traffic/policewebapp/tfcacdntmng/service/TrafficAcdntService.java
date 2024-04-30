package com.moz.ates.traffic.policewebapp.tfcacdntmng.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.moz.ates.traffic.common.component.accident.TrafficAccidentComponent;
import com.moz.ates.traffic.common.component.accident.TrafficAccidentIntegrationDto;
import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntFileInfo;
import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntMaster;
import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntTrgtInfo;
import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntTrgtPnrInfo;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntFileInfoRepository;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntMasterRepository;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntTrgtInfoRepository;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntTrgtPnrInfoRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrafficAcdntService {

	final TrafficAccidentComponent trafficAccidentComponent;
	
	final MozTfcAcdntFileInfoRepository tfcAcdntFileInfoRepository;
	
	final MozTfcAcdntMasterRepository mozTfcAcdntMasterRepository;
	
	final MozTfcAcdntTrgtInfoRepository mozTfcAcdntTrgtInfoRepository;
	
	final MozTfcAcdntTrgtPnrInfoRepository mozTfcAcdntTrgtPnrInfoRepository;
	
	@Value("${file.path.enforcement}")
	private String filePath;
	
	public void regTrafficAcdntInfo(Map<String, Object> paramMap) {
//		//사고 등록 정보 SIZE
//		int tfcAcdntInfoSize = Integer.parseInt((String)paramMap.get("trafficAcdntSize"));
//		
//		//교통사고차량 그룹아이디
//		String acdntVhGrpId = MozatesCommonUtils.getUuid();
//		
//		for(int i = 1; i < tfcAcdntInfoSize+1; i++) {
//			
//			//vio_info_tb Insert Map
//			MozVioInfo vioInfo = new MozVioInfo();
//			String vioId = MozatesCommonUtils.getUuid();
//			String dvrLcenId 	= paramMap.get("dvrLcenId"+i) 	!= null ? String.valueOf(paramMap.get("dvrLcenId"+i)) 	: "";
//			String vioNm 		= paramMap.get("vioNm"+i) 		!= null ? String.valueOf(paramMap.get("vioNm"+i)) 		: "";
//			String vioBrth 		= paramMap.get("vioBrth"+i) 	!= null ? String.valueOf(paramMap.get("vioBrth"+i)) 	: "";
//			String vioAddr 		= paramMap.get("vioAddr"+i) 	!= null ? String.valueOf(paramMap.get("vioAddr"+i)) 	: "";
//			String vioPno 		= paramMap.get("vioPno"+i) 		!= null ? String.valueOf(paramMap.get("vioPno"+i)) 		: "";
//			String vioEmail 	= paramMap.get("vioEmail"+i) 	!= null ? String.valueOf(paramMap.get("vioEmail"+i)) 	: "";
//			String dvrLcenTy 	= paramMap.get("dvrLcenTy"+i) 	!= null ? String.valueOf(paramMap.get("dvrLcenTy"+i)) 	: "";
//			String crtr 		= paramMap.get("polNm") 		!= null ? String.valueOf(paramMap.get("polNm")) 		: "";
//			
//			vioInfo.setVioId(vioId);
//			vioInfo.setDvrLcenId(dvrLcenId);
//			vioInfo.setVioNm(vioNm);
//			vioInfo.setVioBrth(vioBrth);
//			vioInfo.setVioAddr(vioAddr);
//			vioInfo.setVioPno(vioPno);
//			vioInfo.setVioEmail(vioEmail);
//			vioInfo.setDvrLcenTy(dvrLcenTy);
//			vioInfo.setCrtr(crtr);
//				
//				vioInfoRepository.insertVioInfo(vioInfo);
//			
//			
//			//tfc_acdnt_master_tb Insert Map
//			MozTfcAcdntMaster tfcAcdntMaster = new MozTfcAcdntMaster();
//			String tfcAcdntId 	=  MozatesCommonUtils.getUuid();
//			String vhRegNo 		= paramMap.get("vhRegNo"+i) 	!= null ? String.valueOf(paramMap.get("vhRegNo"+i)) 	: "";
//			String vhTy 		= paramMap.get("vhTy"+i) 		!= null ? String.valueOf(paramMap.get("vhTy"+i)) 		: "";
//			Float lat 			= paramMap.get("lat"+i) 		!= null ? (Float) paramMap.get("lat"+i)	: null;
//			Float lng 			= paramMap.get("lng"+i) 		!= null ? (Float) paramMap.get("lng"+i) : null;
//			String roadAddr 	= paramMap.get("roadAddr"+i) 	!= null ? String.valueOf(paramMap.get("roadAddr"+i)) 	: "";
//			String polId 		= paramMap.get("polId") 		!= null ? String.valueOf(paramMap.get("polId")) 		: "";
//			
//			tfcAcdntMaster.setTfcAcdntId(tfcAcdntId);
////			tfcAcdntMaster.setAcdntVhGrpId(acdntVhGrpId);
////			tfcAcdntMaster.setVhRegNo(vhRegNo);
////			tfcAcdntMaster.setVhTy(vhTy);
//			tfcAcdntMaster.setLat(lat);
//			tfcAcdntMaster.setLng(lng);
//			tfcAcdntMaster.setRoadAddr(roadAddr);
//			tfcAcdntMaster.setCrtr(crtr);
//			tfcAcdntMaster.setPolId(polId);
//			tfcAcdntMaster.setLastAcdntPrcCd("TAP001"); //TAP001 = > 교통 사고 등록
//			tfcAcdntMasterRepository.insertAcdnt(tfcAcdntMaster);
//			
//			// 탑승자 정보 SIZE
//			int pnrSize = Integer.parseInt((String)paramMap.get("pnrSize"+i));
//			
//			if( pnrSize > 0 ) {
//				for(int j = 1; j < pnrSize+1; j++) {
//					//tfc_acdnt_master_tb Insert Map
//					MozTfcAcdntTrgtPnrInfo tfcAcdntPnrInfo = new MozTfcAcdntTrgtPnrInfo();
//					String dvrId 		= MozatesCommonUtils.getUuid();
//					String pnrNm 		= paramMap.get("pnrNm"+i+"_"+j) 		!= null ? String.valueOf(paramMap.get("pnrNm"+i+"_"+j)) 		: "";
//					String pnrBrth		= paramMap.get("pnrBrth"+i+"_"+j) 		!= null ? String.valueOf(paramMap.get("pnrBrth"+i+"_"+j)) 		: "";
//					String pnrAddr		= paramMap.get("pnrAddr"+i+"_"+j) 		!= null ? String.valueOf(paramMap.get("pnrAddr"+i+"_"+j)) 		: "";
//					String pnrPno 		= paramMap.get("pnrPno"+i+"_"+j) 		!= null ? String.valueOf(paramMap.get("pnrPno"+i+"_"+j)) 		: "";
//					String pnrEmail 	= paramMap.get("pnrEmail"+i+"_"+j) 		!= null ? String.valueOf(paramMap.get("pnrEmail"+i+"_"+j)) 		: "";
//					String dvYn 		= paramMap.get("dvYn"+i+"_"+j) 			!= null ? String.valueOf(paramMap.get("dvYn"+i+"_"+j)) 			: "";
//					String pnrDvrLcenTy	= paramMap.get("dvrLcenTy"+i+"_"+j) 	!= null ? String.valueOf(paramMap.get("dvrLcenTy"+i+"_"+j)) 	: "";
//					
////					tfcAcdntPnrInfo.setDvrId(dvrId);
//					tfcAcdntPnrInfo.setTfcAcdntId(tfcAcdntId);
////					tfcAcdntPnrInfo.setDvrLcenId(dvrLcenId);
//					tfcAcdntPnrInfo.setPnrNm(pnrNm);
//					tfcAcdntPnrInfo.setPnrBrth(pnrBrth);
//					tfcAcdntPnrInfo.setPnrAddr(pnrAddr);
//					tfcAcdntPnrInfo.setPnrPno(pnrPno);
//					tfcAcdntPnrInfo.setPnrEmail(pnrEmail);
////					tfcAcdntPnrInfo.setDvYn(dvYn);
//					tfcAcdntPnrInfo.setDvrLcenTy(pnrDvrLcenTy);
//					tfcAcdntPnrInfo.setCrtr(crtr);
////					tfcAcdntPnrInfoRepository.insertPnrAcdntInfo(tfcAcdntPnrInfo);
//				}
//			}
//						
//			//vio_info_tb Insert Map
//			MozTfcEnfMaster tfcEnfMaster = new MozTfcEnfMaster();
//			String tfcEnfId = MozatesCommonUtils.getTfcEnfId();
//			String tfcLawId 	= paramMap.get("tfcLawId"+i) 	!= null ? String.valueOf(paramMap.get("tfcLawId"+i)) 	: "";
//			String roadLn 		= paramMap.get("roadLn"+i) 		!= null ? String.valueOf(paramMap.get("roadLn"+i)) 		: "";
//			Long spdLmt 		= paramMap.get("spdLmt"+i) 		!= null ? (Long) paramMap.get("spdLmt"+i) 	: null;
//			Long vioSpd 		= paramMap.get("vioSpd"+i) 		!= null ? (Long) paramMap.get("vioSpd"+i) 	: null;
//			Long overSpd 		= paramMap.get("overSpd"+i) 	!= null ? (Long) paramMap.get("overSpd"+i) 	: null;
//			
//			tfcEnfMaster.setTfcEnfId(tfcEnfId);
////			tfcEnfMaster.setTfcLawId(tfcLawId);
//			tfcEnfMaster.setVioId(vioId);
//			tfcEnfMaster.setPolId(polId);
//			tfcEnfMaster.setRoadAddr(roadAddr);
//			tfcEnfMaster.setRoadLn(roadLn);
//			tfcEnfMaster.setVhRegNo(vhRegNo);
//			tfcEnfMaster.setVhTy(vhTy);
//			tfcEnfMaster.setSpdLmt(spdLmt);
//			tfcEnfMaster.setVioSpd(vioSpd);
//			tfcEnfMaster.setOverSpd(overSpd);
//			tfcEnfMaster.setLng(lng);
//			tfcEnfMaster.setLat(lat);
//			tfcEnfMaster.setLastTfcEnfProcCd("TEP001");		//last_tfc_enf_proc_cd TEP001 = > 등록
//			tfcEnfMaster.setCrtr(crtr);
//			tfcEnfMasterRepository.insertTfcEnfInfo(tfcEnfMaster);
//			
//			//fine_pymnt_info_tb Insert Map
//			MozFinePymntInfo finePymntInfo = new MozFinePymntInfo();
//			String pymntId = MozatesCommonUtils.getUuid();
//			String placePymntId 	= paramMap.get("placePymntId"+i) 	!= null ? String.valueOf(paramMap.get("placePymntId"+i)) 	: "";
//			String totalPrice 		= paramMap.get("totalPrice"+i) 		!= null ? String.valueOf(paramMap.get("totalPrice"+i)) 		: "";
//			String pymntPrice 		= paramMap.get("pymntPrice"+i) 		!= null ? String.valueOf(paramMap.get("pymntPrice"+i)) 		: "";
//			String pymntOprtr 		= paramMap.get("pymntOprtr"+i) 		!= null ? String.valueOf(paramMap.get("pymntOprtr"+i)) 		: "";
//			
//			//PYMNT_DT, PYMNT_DDLN 납부 날짜 / 기한 확인필요
//			finePymntInfo.setPymntId(pymntId);
////			finePymntInfo.setTfcEnfId(tfcEnfId);
//			finePymntInfo.setPayerNm(vioNm);
//			finePymntInfo.setPlacePymntId(placePymntId);
//			finePymntInfo.setTotalPrice(Float.parseFloat(totalPrice));
//			finePymntInfo.setPymntPrice(Float.parseFloat(pymntPrice));
//			finePymntInfo.setPymntOprtr(pymntOprtr);
//			finePymntInfo.setPymntStts("N");
//			finePymntInfo.setCrtr(crtr);
//			finePymntInfoRepository.insertFinePaymentInfo(finePymntInfo);
//			
//			//tfc_acdnt_chg_hst_tb Insert Map
//			MozTfcAcdntChgHst tfcAcdntChgHst = new MozTfcAcdntChgHst();
//			String hstId = MozatesCommonUtils.getUuid();
//			tfcAcdntChgHst.setHstId(hstId);
//			tfcAcdntChgHst.setTfcAcdntId(tfcAcdntId);
//			tfcAcdntChgHst.setAcdntPrcCd("TAP001"); //TAP001 = > 교통 사고 등록
//			tfcAcdntChgHst.setCrtr(crtr);
//			tfcAcdntChgHstRepository.insertTfcAcdntHstInfo(tfcAcdntChgHst);
//			
//			
//			//tfc_enf_hst_tb Insert Map
//			MozTfcEnfHst tfcEnfHst = new MozTfcEnfHst();
//			String enfHstId = MozatesCommonUtils.getUuid();
//			tfcEnfHst.setHstId(enfHstId); 
//			tfcEnfHst.setTfcEnfId(tfcEnfId);
//			tfcEnfHst.setTfcEnfPrcCd("TEP001");	//last_tfc_enf_proc_cd TEP001 = > 등록
//			tfcEnfHst.setCrtr(crtr);
//			tfcEnfHstRepository.insertTfcEnfHstInfo(tfcEnfHst);
//		}
	}


	public String regTrafficAcdntInfo(TrafficAccidentIntegrationDto trafficAccidentIntegrationDto, String polId,
			MultipartFile[] files) {
  		MozTfcAcdntMaster accidentMaster = trafficAccidentComponent.register(trafficAccidentIntegrationDto, polId);

		BufferedOutputStream bos = null;
		String date = DateFormatUtils.format(new Date(), "yyyyMMdd");
		if(files != null && files.length > 0){
			for (MultipartFile file : files) {
				String name = MozatesCommonUtils.getUuid();
				String ext = FilenameUtils.getExtension(file.getOriginalFilename());
				String filename = name + "." + ext;
				try {
					byte[] bytes = file.getBytes();
					String uploadFilePath = filePath + File.separator + date + File.separator + accidentMaster.getTfcAcdntId();
					File dir = new File(uploadFilePath);
					if (!dir.exists()) dir.mkdirs();
					String uploadPath = uploadFilePath + File.separator + filename;
					File serverFile = new File(uploadPath);
					bos = new BufferedOutputStream(new FileOutputStream(serverFile));
					bos.write(bytes);

					MozTfcAcdntFileInfo uploadFile = new MozTfcAcdntFileInfo();
					uploadFile.setTfcAcdntId(accidentMaster.getTfcAcdntId());
					uploadFile.setAcdntFileNo(name);
					uploadFile.setFilePath(uploadPath);
					uploadFile.setFileExt(ext);
					uploadFile.setFileOriginNm(file.getOriginalFilename());
					uploadFile.setFileNm(filename);
					uploadFile.setFileSize(file.getSize());
					uploadFile.setCrtr(polId);
					tfcAcdntFileInfoRepository.insertMozTfcAcdntFileInfo(uploadFile);
				} catch (IOException ignored) {
					log.info("File upload failed");
				} finally {
					try {
						if (bos != null) bos.close();
					} catch (IOException ignored) {
					}
				}
			}
		}
		return accidentMaster.getTfcAcdntId();
	}
	
	public int getAcdntCount(MozTfcAcdntMaster mozTfcAcdntMaster) {
		return mozTfcAcdntMasterRepository.countAllTfcEnfHistoryList(mozTfcAcdntMaster);
	}

	/**
	  * @Method Name : getTfcAcdntList
	  * @Date : 2024. 3. 21.
	  * @Author : IK.MOON
	  * @Method Brief : 사고이력 리스트 조회
	  * @param mozTfcAcdntMaster
	  * @return
	  */
	public List<MozTfcAcdntMaster> getTfcAcdntList(MozTfcAcdntMaster mozTfcAcdntMaster) {
		
		return mozTfcAcdntMasterRepository.findAllTfcEnfHistoryList(mozTfcAcdntMaster);
	}
	
	
	public MozTfcAcdntMaster getTfcAcdntDetail(String tfcAcdntId) {
		MozTfcAcdntMaster tfcAcdntDetail = mozTfcAcdntMasterRepository.findOneTfcAcdntHistory(tfcAcdntId);
		
		if (MozatesCommonUtils.isNull(tfcAcdntDetail)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}
		
		// Target Info
		List<MozTfcAcdntTrgtInfo> tfcAcdntTrgtInfoList = 
		mozTfcAcdntTrgtInfoRepository.findAllTfcAcdntTrgtByTfcAcdntIdOrderByAcdntTrgtSortNo(tfcAcdntId);
		
		tfcAcdntDetail.setTfcAcdntTrgtInfo(tfcAcdntTrgtInfoList);
		
		
		// Passenger Info
		List<MozTfcAcdntTrgtPnrInfo> tfcAcdntTrgtPnrInfoList = 
		mozTfcAcdntTrgtPnrInfoRepository.findAllTfcAcdntTrgtInfoByTfcAcdntIdOrderByPnrSortNo(tfcAcdntId);
		
		tfcAcdntDetail.setTfcAcdntPnrInfo(tfcAcdntTrgtPnrInfoList);
		
		// File Info
		List<MozTfcAcdntFileInfo> tfcAcdntFileInfo = tfcAcdntFileInfoRepository.findAllTfcAcdntFileInfoByTfcAcdntId(tfcAcdntId);
		
		if (!MozatesCommonUtils.isNull(tfcAcdntFileInfo)) {
			tfcAcdntDetail.setTfcAcdntFileInfo(tfcAcdntFileInfo);
		}
		
		return tfcAcdntDetail;
	}
}
