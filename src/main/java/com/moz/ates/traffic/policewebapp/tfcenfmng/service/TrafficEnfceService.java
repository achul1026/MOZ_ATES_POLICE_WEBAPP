package com.moz.ates.traffic.policewebapp.tfcenfmng.service;

import com.moz.ates.traffic.common.component.enforcement.TrafficEnforcementComponent;
import com.moz.ates.traffic.common.component.enforcement.TrafficEnforcementIntegrationDto;
import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.entity.equipment.MozTfcEnfFileInfo;
import com.moz.ates.traffic.common.entity.equipment.MozTfcEnfFineInfo;
import com.moz.ates.traffic.common.entity.law.MozTfcLwFineInfo;
import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.common.entity.payment.MozPlPymntInfo;
import com.moz.ates.traffic.common.repository.driver.MozVioInfoRepository;
import com.moz.ates.traffic.common.repository.enforcement.MozTfcEnfHstRepository;
import com.moz.ates.traffic.common.repository.enforcement.MozTfcEnfMasterRepository;
import com.moz.ates.traffic.common.repository.equipment.MozTfcEnfFileInfoRepository;
import com.moz.ates.traffic.common.repository.equipment.MozTfcEnfFineInfoRepository;
import com.moz.ates.traffic.common.repository.law.MozTfcLwFineInfoRepository;
import com.moz.ates.traffic.common.repository.law.MozTfcLwInfoRepository;
import com.moz.ates.traffic.common.repository.payment.MozFinePymntInfoRepository;
import com.moz.ates.traffic.common.repository.payment.MozPlPymntInfoRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrafficEnfceService{
	
	final MozTfcLwInfoRepository tfcLwInfoRepository;

	final MozPlPymntInfoRepository plPymntInfoRepository;

	final MozVioInfoRepository vioInfoRepository;

	final MozTfcEnfMasterRepository mozTfcEnfMasterRepository;

	final MozTfcEnfHstRepository tfcEnfHstRepository;

	final MozFinePymntInfoRepository finePymntInfoRepository;

	final TrafficEnforcementComponent trafficEnforcementComponent;

	final MozTfcEnfFileInfoRepository mozTfcEnfFileInfoRepository;
	
	final MozTfcLwFineInfoRepository mozTfcLwFineInfoRepository; 
	
	final MozTfcEnfFineInfoRepository mozTfcEnfFineInfoRepository; 

	@Value("${file.path.enforcement}")
	private String filePath;
	
	public List<MozTfcEnfMaster> getTfcEnfList(MozTfcEnfMaster tfcEnfMaster) {
		return mozTfcEnfMasterRepository.findAllTfcEnfMasterHistory(tfcEnfMaster);
	}
	
	public int getEnfCount(MozTfcEnfMaster tfcEnfMaster) {
		return mozTfcEnfMasterRepository.countAllTfcEnfMasterHistory(tfcEnfMaster);
	}
	
	/**
	  * @Method Name : getTfcEnfDetail
	  * @Date : 2024. 3. 19.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 상세정보 조회
	  * @param tfcEnfId
	  * @return
	  */
	public MozTfcEnfMaster getTfcEnfDetail(String tfcEnfId) {
		MozTfcEnfMaster mozEnfMaster = mozTfcEnfMasterRepository.findOneTfcEnfInfoByTfcEnfId(tfcEnfId);
		
		if(MozatesCommonUtils.isNull(mozEnfMaster)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}
		
		// uploaded files
		List<MozTfcEnfFileInfo> fileList = mozTfcEnfFileInfoRepository.findTfcEnfFileInfoByTfcEnfId(tfcEnfId);
		if(fileList != null && !fileList.isEmpty()) {
			mozEnfMaster.setFileList(fileList);
		}
		
		return mozEnfMaster;
	}
	
	/**
	  * @Method Name : getAllTfcEnfFineInfo
	  * @Date : 2024. 3. 19.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 정보 위반 법률, 범칙금 조회
	  * @param tfcEnfId
	  * @return
	  */
	public List<MozTfcEnfFineInfo> getAllTfcEnfFineInfo(String tfcEnfId) {
		return mozTfcEnfFineInfoRepository.findAllTfcEnfFineInfoJoinTfcLwFineInfoAndTfcLwInfoByTfcEnfId(tfcEnfId);
	}

	public List<MozTfcLwInfo> getTrafficLawsList() {
		MozTfcLwInfo tfcLwInfo = new MozTfcLwInfo();
		return tfcLwInfoRepository.findAllLawListsByTfcLwInfo(tfcLwInfo);
	}
	
	public List<MozTfcLwInfo> getTrafficLawsListByNotNullFineInfo() {
		return tfcLwInfoRepository.findAllLawListsIsNotNullFineInfo();
	}

	public List<MozPlPymntInfo> getPlacePaymentList() {
		MozPlPymntInfo plPymntInfo = new MozPlPymntInfo();
		return plPymntInfoRepository.findAllPlacePaymentList(plPymntInfo);
	}
	
	public List<MozTfcLwFineInfo> getLawFineInfoList(String tfcLawId){
		MozTfcLwFineInfo tfcLwFineInfo = new MozTfcLwFineInfo();
		tfcLwFineInfo.setTfcLawId(tfcLawId);
		return mozTfcLwFineInfoRepository.findMozTfcLwFineInfoByTfcLawId(tfcLawId);
	}

	public String regTrafficEnfceInfo(TrafficEnforcementIntegrationDto trafficEnforcementIntegrationDto, String policeId, MultipartFile[] files) {
		MozTfcEnfMaster enforcementMaster = trafficEnforcementComponent.register(trafficEnforcementIntegrationDto, policeId);

		BufferedOutputStream bos = null;
		String date = DateFormatUtils.format(new Date(), "yyyyMMdd");
		if(files != null && files.length > 0){
			for (MultipartFile file : files) {
				String name = MozatesCommonUtils.getUuid();
				String ext = FilenameUtils.getExtension(file.getOriginalFilename());
				String filename = name + "." + ext;
				try {
					byte[] bytes = file.getBytes();
					String uploadFilePath = filePath + File.separator + date + File.separator + enforcementMaster.getTfcEnfId();
					File dir = new File(uploadFilePath);
					if (!dir.exists()) dir.mkdirs();
					String uploadPath = uploadFilePath + File.separator + filename;
					File serverFile = new File(uploadPath);
					bos = new BufferedOutputStream(new FileOutputStream(serverFile));
					bos.write(bytes);

					MozTfcEnfFileInfo uploadFile = new MozTfcEnfFileInfo();
					uploadFile.setTfcEnfId(enforcementMaster.getTfcEnfId());
					uploadFile.setFilePath(uploadPath);
					uploadFile.setFileOrgNm(file.getOriginalFilename());
					uploadFile.setFileNm(filename);
					uploadFile.setFileSize(String.valueOf(file.getSize()));
					mozTfcEnfFileInfoRepository.insertTfcEnfFileInfo(uploadFile);
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
		return enforcementMaster.getTfcEnfId();
		
	}
	
}
