package com.moz.ates.traffic.policewebapp.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.moz.ates.traffic.common.component.Pagination;
import com.moz.ates.traffic.common.entity.board.MozBrd;
import com.moz.ates.traffic.policewebapp.notice.service.NoticeService;

@Controller
@RequestMapping(value = "/notice")
public class NoticeController {

	@Autowired
	NoticeService noticeService;
	
	/**
	  * @Method Name : getNoticeList
	  * @Date : 2024. 1. 15.
	  * @Author : IK.MOON
	  * @Method Brief : 공지사항 리스트 페이지 이동
	  * @param model
	  * @return
	  */
	@GetMapping(value="/list")
	public String getNoticeList(Model model, MozBrd mozBrd) {
		int page = mozBrd.getPage();
		int totalCnt = noticeService.getNoticeCount(mozBrd);
		Pagination pagination = new Pagination(totalCnt, page, 5, 5);
		
		mozBrd.setLength(5);
		mozBrd.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("mozBrd", mozBrd);
		model.addAttribute("noticeList", noticeService.getNoticeList(mozBrd));
		model.addAttribute("pagination", pagination);
		return "views/notice/noticeList";
	}
	
	/**
	  * @Method Name : getNoticeDetail
	  * @Date : 2024. 1. 15.
	  * @Author : IK.MOON
	  * @Method Brief : 공지사항 상세 페이지 이동
	  * @param model
	  * @return
	  */
	@GetMapping(value="/detail/{brdIdx}")
	public String getNoticeDetail(@PathVariable String brdIdx, Model model) {
		model.addAttribute("noticeDetail", noticeService.getNoticeDetail(brdIdx));
		return "views/notice/noticeDetail";
	}
}
