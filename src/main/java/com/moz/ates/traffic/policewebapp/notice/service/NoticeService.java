package com.moz.ates.traffic.policewebapp.notice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moz.ates.traffic.common.entity.board.MozBrd;
import com.moz.ates.traffic.common.enums.NoticeCateCd;
import com.moz.ates.traffic.common.repository.board.MozBrdRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;

@Service
public class NoticeService {
	
	@Autowired
	MozBrdRepository mozBrdRepository;
	
	/**
	  * @Method Name : getNoticeList
	  * @Date : 2024. 3. 8.
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
	  * @Method Name : getNoticeCount
	  * @Date : 2024. 3. 8.
	  * @Author : IK.MOON
	  * @Method Brief : 공지사항 count 조회
	  * @param mozBrd
	  * @return
	  */
	public int getNoticeCount(MozBrd mozBrd) {
		mozBrd.setUseYn("Y");
		mozBrd.setCateCd(NoticeCateCd.POLICE_APPLICATION.getCode());
		return mozBrdRepository.countByUseYn(mozBrd);
	}
	
	/**
	  * @Method Name : getNoticeDetail
	  * @Date : 2024. 3. 6.
	  * @Author : IK.MOON
	  * @Method Brief : 공지사항 상세 조회
	  * @param brdIdx
	  * @return
	  */
	public MozBrd getNoticeDetail(String brdIdx) {
		if(MozatesCommonUtils.isNull(brdIdx)) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}
		
		MozBrd mozBrd = new MozBrd();
		mozBrd.setBoardIdx(brdIdx);
		mozBrd.setUseYn("Y");
		mozBrd.setCateCd(NoticeCateCd.POLICE_APPLICATION.getCode());
		
		mozBrdRepository.updateViewCnt(mozBrd);
		MozBrd noticeDetail = mozBrdRepository.findOneByBoardIdxAndUseYnAndCateCd(mozBrd);
		
		if(MozatesCommonUtils.isNull(mozBrd)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}
		
		return noticeDetail;
	}
}
