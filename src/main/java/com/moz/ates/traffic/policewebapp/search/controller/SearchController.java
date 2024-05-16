package com.moz.ates.traffic.policewebapp.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moz.ates.traffic.common.entity.api.MojApiRequest;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.policewebapp.search.service.SearchService;

@Controller
@RequestMapping(value = "/search")
public class SearchController {
	
	@Autowired
	SearchService searchService;
	
    /**
     * @Method Name : searchDriver
     * @Date : 2024. 1. 15.
     * @Author : IK.MOON
     * @Method Brief : 운전자 조회 페이지 이동
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String searchList(Model model) {

        return "views/main/search";
    }
    
    /**
     * @brief search상세
     * @author KY.LEE
     * @date 2024. 5. 7.
     * @method viewSearchDetail
     */
    @PostMapping("/detail")
    public String viewSearchDetail(Model model, @ModelAttribute MojApiRequest mojApiRequest) {
		if(mojApiRequest != null && MozatesCommonUtils.isNull(mojApiRequest.getNumerododocumento())) {
			throw new CommonException(ErrorCode.INVALID_PARAMETER);
		}
		
		switch(mojApiRequest.getRegType()) {
		case "enf":
			model.addAttribute("violationList", searchService.getViolationInfoListByDocNid(mojApiRequest.getNumerododocumento()));
			break;
		case "acdnt":
			model.addAttribute("acdntTrgtList", searchService.getAcdntTrgtList(mojApiRequest.getNumerododocumento()));
			break;
		
		}
		
		if(mojApiRequest != null && !MozatesCommonUtils.isNull(mojApiRequest.getDatadenascimento())) {
			String birthDayFormat = MozatesCommonUtils.changeDateFormat(mojApiRequest.getDatadenascimento(), "yyyy-MM-dd'T'HH:mm:ss", "dd.MM.yyyy");
			mojApiRequest.setVioBrth(birthDayFormat);
			mojApiRequest.setVioAddr(MozatesCommonUtils.formatAddress(mojApiRequest.getDomicilio(), mojApiRequest.getProvincia(), mojApiRequest.getDistrito()));
		}
		
		model.addAttribute("driverInfo", mojApiRequest);
    	return "views/main/searchDetail";
    }
    
    @GetMapping("/acdntEnfHistory")
    public String acdntEnfHistory(Model model) {

        return "views/main/acdntEnfHistory";
    }

    @GetMapping("/acdntEnfceDetail")
    public String acdntEnfceDetail(Model model) {

        return "views/main/acdntEnfceDetail";
    }
}
