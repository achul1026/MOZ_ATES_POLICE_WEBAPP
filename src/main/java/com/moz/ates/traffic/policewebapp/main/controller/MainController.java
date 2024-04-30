package com.moz.ates.traffic.policewebapp.main.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntMaster;
import com.moz.ates.traffic.common.entity.board.MozBrd;
import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.policewebapp.main.service.MainService;

@Controller
@RequestMapping(value = "/main")
public class MainController {
	
	@Autowired
	MainService mainService;
	
	/**
	  * @Method Name : getMainPage
	  * @Date : 2024. 1. 15.
	  * @Author : IK.MOON
	  * @Method Brief : 메인 페이지 이동
	  * @param model
	  * @return
	  */
	@GetMapping(value="")
	public String getMainPage(Model model, MozBrd mozBrd
			, MozTfcEnfMaster mozTfcEnfMaster, MozTfcAcdntMaster mozTfcAcdntMaster) {
		
	  Locale portugueseLocale = Locale.forLanguageTag("pt-PT");
		Month currentMonth = MonthDay.now().getMonth();
		String monthFullName = currentMonth.getDisplayName(TextStyle.FULL, portugueseLocale);

		monthFullName = Character.toUpperCase(monthFullName.charAt(0)) + monthFullName.substring(1);
		// English
		// String monthFullName = currentMonth.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		
		
    String firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString();
    String lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).toString();
    
    mozTfcEnfMaster.setSDate(firstDayOfMonth);
    mozTfcEnfMaster.setEDate(lastDayOfMonth);
    
    mozTfcAcdntMaster.setSDate(firstDayOfMonth);
    mozTfcAcdntMaster.setEDate(lastDayOfMonth);
    
		mozBrd.setLength(3);
		mozBrd.setStart(0);
		
		model.addAttribute("noticeList", mainService.getNoticeList(mozBrd));
		model.addAttribute("tfcEnfCount", mainService.getTfcEnfCount(mozTfcEnfMaster));
		model.addAttribute("tfcAcdntCount", mainService.getTfcAcdntCount(mozTfcAcdntMaster));
		model.addAttribute("monthFullName", monthFullName);
		return "views/main/main";
	}

}
