package com.moz.ates.traffic.policewebapp.tfcacdntmng.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moz.ates.traffic.common.component.FileUploadComponent;
import com.moz.ates.traffic.common.component.Pagination;
import com.moz.ates.traffic.common.component.accident.TrafficAccidentIntegrationDto;
import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntFileInfo;
import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntMaster;
import com.moz.ates.traffic.common.entity.api.MojApiRequest;
import com.moz.ates.traffic.common.entity.common.CommonResponse;
import com.moz.ates.traffic.common.entity.police.MozPolInfo;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntFileInfoRepository;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.policewebapp.config.constants.PoliceAppConstants;
import com.moz.ates.traffic.policewebapp.tfcacdntmng.service.TrafficAcdntService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/tfcacdntmng")
@RequiredArgsConstructor
public class TrafficAcdntController {

	private final TrafficAcdntService trafficAcdntService;
	
	private final FileUploadComponent fileUploadComponent;
	
	@Autowired
	MozTfcAcdntFileInfoRepository mozTfcAcdntFileInfoRepository;
	
	@GetMapping(value="/trafficAcdntRegPage")
	public String trafficAcdntRegPage(Model model, @ModelAttribute MojApiRequest mojApiRequest) {
		if(mojApiRequest != null && !MozatesCommonUtils.isNull(mojApiRequest.getDatadenascimento())) {
			String birthDayFormat = MozatesCommonUtils.changeDateFormat(mojApiRequest.getDatadenascimento(), "yyyy-MM-dd'T'HH:mm:ss", "dd.MM.yyyy");
			mojApiRequest.setVioBrth(birthDayFormat);
			mojApiRequest.setVioAddr(MozatesCommonUtils.formatAddress(mojApiRequest.getDomicilio(), mojApiRequest.getProvincia(), mojApiRequest.getDistrito()));
		}
		
		model.addAttribute("apiDriverInfo",mojApiRequest); 
		return "views/tfcacdntmng/trafficAcdntRegPage";
	}

	@GetMapping(value="/trafficAcdntPasnrAddLayer")
	public String modalViewAccidentPassenger(Model model, 
			@RequestParam(name = "partyIdx") Long partyIdx,
			@RequestParam(name = "passengerIdx") Long passengerIdx,
			@RequestParam(name = "passengerInfoStr", required = false) String passengerInfoStr 
			) {
		Map<String,Object> passengerInfo = null;
		
		if(!MozatesCommonUtils.isNull(passengerInfoStr)) {
			passengerInfo = MozatesCommonUtils.jsonStringToMap(passengerInfoStr);
		}
		
		model.addAttribute("partyIdx", partyIdx);
		model.addAttribute("passengerIdx", passengerIdx);
		model.addAttribute("passengerInfo", passengerInfo);
		
		return "views/tfcacdntmng/trafficAcdntPasnrAddLayer";
	}
	
	@GetMapping(value="/trafficAcdntRegionInfo/save.do")
	public String trafficAcdntRegionInfo(Model model) {
		
		return "views/tfcacdntmng/trafficAcdntRegionInfo";
	}
	
	@GetMapping(value="/trafficAcdntCheckSearch/save.do")
	public String trafficAcdntCheckSearch(Model model) {
		
		return "views/tfcacdntmng/trafficAcdntCheckSearch";
	}
	
	@GetMapping(value="/trafficAcdntInfoRegist/save.do")
	public String trafficAcdntInfoRegist(Model model) {
		
		return "views/tfcacdntmng/trafficAcdntInfoRegist";
	}
	@GetMapping(value="/trafficAcdntAllInfo/save.do")
	public String trafficAcdntAllInfo(Model model) {
		return "views/tfcacdntmng/trafficAcdntAllInfo";
	}

	@PostMapping(value="/trafficAcdntRegPageRegister")
	@ResponseBody
	public CommonResponse<?> trafficAcdntReg(
			TrafficAccidentIntegrationDto trafficAccidentIntegrationDto,
			@RequestParam(name = "files", required = false) MultipartFile[] files,
			Authentication authentication,
			RedirectAttributes redirectAttributes
	) {
		String tfcAcdntId = "";
		try {
			//로그인정보에서 경찰 고유 ID 가져오기
			String polId = authentication.getName();

			//위반 정보 등록
 			tfcAcdntId = trafficAcdntService.regTrafficAcdntInfo(trafficAccidentIntegrationDto, polId, files);
		} catch (Exception e) {
			e.printStackTrace();
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Registration Failed");
		}
		return CommonResponse.ResponseSuccess(HttpStatus.OK, "Registration Success", null, tfcAcdntId);
	}
	
	@PostMapping(value="/trafficAcdntReg")
	public String trafficAcdntReg(Model model,@RequestParam Map<String,Object> paramMap,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		
		try {
			//로그인정보에서 경찰 고유 ID 가져오기
			MozPolInfo policeInfo = (MozPolInfo) session.getAttribute(PoliceAppConstants.POLICE_SESSION_KEY);
			String polId = policeInfo.getPolId(); 
			String polNm = policeInfo.getPolNm();
			session.getAttribute(PoliceAppConstants.POLICE_SESSION_KEY);
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
	
	/**
	  * @Method Name : getAcdntHistoryList
	  * @Date : 2024. 3. 14.
	  * @Author : IK.MOON
	  * @Method Brief : 사고 이력 조회
	  * @param mozTfcEnfMaster
	  * @param model
	  * @return
	  */
	@GetMapping(value="/list")
	public String getAcdntHistoryList(MozTfcAcdntMaster mozTfcAcdntMaster, Model model) {
		int page = mozTfcAcdntMaster.getPage();
		int totalCnt = trafficAcdntService.getAcdntCount(mozTfcAcdntMaster);
		Pagination pagination = new Pagination(totalCnt, page, 5, 5);
		
		mozTfcAcdntMaster.setLength(5);
		mozTfcAcdntMaster.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("enfAcdntMaster", mozTfcAcdntMaster);
		model.addAttribute("pagination", pagination);
		model.addAttribute("acdntList", trafficAcdntService.getTfcAcdntList(mozTfcAcdntMaster));
		
		return "views/tfcacdntmng/trafficAcdntList";
	}
	
	/**
	  * @Method Name : gethistoryDetail
	  * @Date : 2024. 3. 14.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 상세 조회
	  * @param idx
	  * @param model
	  * @return
	  */
	@GetMapping(value="/detail")
	public String gethistoryDetail(@RequestParam(name="tfcAcdntId", required = true) String tfcAcdntId, Model model) {
		model.addAttribute("acdntDetail", trafficAcdntService.getTfcAcdntDetail(tfcAcdntId));
		return "views/tfcacdntmng/trafficAcdntDetail";
	}

	
	/**
	  * @Method Name : tfcEnfFileView
	  * @Date : 2024. 3. 22.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 첨부 이미지 요청
	  * @param response
	  * @param vioFileNo
	 * @throws IOException 
	  */
	@GetMapping("/tfcAcdnt/image")
	public void tfcEnfFileView(HttpServletResponse response,
			@RequestParam(name = "acdntFileNo" ,required = true) String acdntFileNo
			) throws IOException   {
		MozTfcAcdntFileInfo tfcAcdntFileInfo = mozTfcAcdntFileInfoRepository.findOneMozTfcAcdntFileInfoByAcdntFileNo(acdntFileNo);
		fileUploadComponent.imgView(response, tfcAcdntFileInfo.getFilePath());
	}
	
}
