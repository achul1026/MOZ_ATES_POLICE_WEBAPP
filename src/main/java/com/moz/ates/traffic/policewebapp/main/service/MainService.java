package com.moz.ates.traffic.policewebapp.main.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntMaster;
import com.moz.ates.traffic.common.entity.board.MozBrd;
import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.enums.NoticeCateCd;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntMasterRepository;
import com.moz.ates.traffic.common.repository.board.MozBrdRepository;
import com.moz.ates.traffic.common.repository.enforcement.MozTfcEnfMasterRepository;

@Service
public class MainService {
	
	@Autowired
	MozBrdRepository mozBrdRepository;
	
	@Autowired
	MozTfcEnfMasterRepository mozTfcEnfMasterRepository;
	
	@Autowired
	MozTfcAcdntMasterRepository mozTfcAcdntMasterRepository;
	
	/**
	  * @Method Name : getNoticeList
	  * @Date : 2024. 3. 6.
	  * @Author : IK.MOON
	  * @Method Brief : 공지사항 리스트 조회
	  * @param mozBrd
	  * @return
	  */
	public List<MozBrd> getNoticeList(MozBrd mozBrd) {
		mozBrd.setUseYn("Y");
		mozBrd.setCateCd(NoticeCateCd.POLICE_APPLICATION.getCode());
		return mozBrdRepository.findAllByUseYn(mozBrd);
	}
	
	/**
	  * @Method Name : getTfcEnfCount
	  * @Date : 2024. 3. 6.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 count 조회
	  * @param mozTfcEnfMaster
	  * @return
	  */
	public int getTfcEnfCount(MozTfcEnfMaster mozTfcEnfMaster) {
		return mozTfcEnfMasterRepository.countAllMozTfcEnfByTfcEnfDt(mozTfcEnfMaster);
	}
	
	/**
	  * @Method Name : getTfcAcdntCount
	  * @Date : 2024. 3. 6.
	  * @Author : IK.MOON
	  * @Method Brief : 사고 count 조회
	  * @param mozTfcAcdntMaster
	  * @return
	  */
	public int getTfcAcdntCount(MozTfcAcdntMaster mozTfcAcdntMaster) {
		return mozTfcAcdntMasterRepository.countAllByAcdntDt(mozTfcAcdntMaster);
	}
	
}
