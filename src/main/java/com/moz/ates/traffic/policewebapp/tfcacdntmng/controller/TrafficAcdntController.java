package com.moz.ates.traffic.policewebapp.tfcacdntmng.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.common.entity.payment.MozPlPymntInfo;
import com.moz.ates.traffic.common.entity.police.MozPolInfo;
import com.moz.ates.traffic.policewebapp.tfcacdntmng.service.TrafficAcdntService;

@Controller
@RequestMapping(value = "/tfcacdntmng")
public class TrafficAcdntController {

	@Autowired
	private TrafficAcdntService trafficAcdntService;
	
	@RequestMapping(value="/trafficAcdntRegPage")
	public String trafficAcdntRegPage(Model model) {
		
		//법률 목록
		List<MozTfcLwInfo> trafficLawList = trafficAcdntService.getTrafficLawsList();
		
		//납부지 목록
		List<MozPlPymntInfo> placePaymentList = trafficAcdntService.getPlacePaymentList();
		
		model.addAttribute("trafficLawList",trafficLawList);
		model.addAttribute("placePaymentList",placePaymentList);
		
		return "views/tfcacdntmng/trafficAcdntRegPage";
	}
	
	@RequestMapping(value="/trafficAcdntReg")
	public String trafficAcdntReg(Model model,@RequestParam Map<String,Object> paramMap,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		try {
			
			//로그인정보에서 경찰 고유 ID 가져오기
			MozPolInfo policeInfo = (MozPolInfo) session.getAttribute("policeInfo");
			String polId = policeInfo.getPolId(); 
			String polNm = policeInfo.getPolNm();
			session.getAttribute("policeInfo");
			paramMap.put("polId", polId);
			paramMap.put("polNm", polNm);
			
			//위반 정보 등록
			trafficAcdntService.regTrafficAcdntInfo(paramMap);
			
			//성공메세지 전달
			redirectAttributes.addFlashAttribute("resultMsg","Registration Success");
			redirectAttributes.addFlashAttribute("resultMsgType","success");
			
		} catch (Exception e) {
			
			//실패메세지 전달
			redirectAttributes.addFlashAttribute("resultMsg","Registration Failed. Please heck the input value.");
			redirectAttributes.addFlashAttribute("resultMsgType","warning");
		}
		
		return "redirect:/tfcacdntmng/trafficAcdntRegPage";
	}
	
}
