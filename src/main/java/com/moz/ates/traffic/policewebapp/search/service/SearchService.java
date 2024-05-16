package com.moz.ates.traffic.policewebapp.search.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntTrgtInfo;
import com.moz.ates.traffic.common.entity.driver.MozVioInfo;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntTrgtInfoRepository;
import com.moz.ates.traffic.common.repository.driver.MozVioInfoRepository;

@Service
public class SearchService {
	
	@Autowired
	MozVioInfoRepository vioInfoRepository;
	
	@Autowired
	MozTfcAcdntTrgtInfoRepository tfcAcdntTrgtInfoRepository;

	/**
	 * @brief : 위반자 정보 조회
	 * @details : 위반자 정보 조회
	 * @author : KY.LEE
	 * @date : 2024.05.02
	 * @param : docNid
	 */
	public List<MozVioInfo> getViolationInfoListByDocNid(String docNid) {
		return vioInfoRepository.findAllViolationInfoListByDocNid(docNid);
	}

	/**
	 * @brief 사고타겟 이력 조회
	 * @details : 사고타겟 이력 조회
	 * @author KY.LEE
	 * @date 2024. 4. 11.
	 * @method getAcdntTrgtListByDocNid
	 */
	public List<MozTfcAcdntTrgtInfo> getAcdntTrgtList(String dvrLcenId) {
		return tfcAcdntTrgtInfoRepository.findAllTfcAcdntTrgtByDvrLcenId(dvrLcenId);
	}
}
