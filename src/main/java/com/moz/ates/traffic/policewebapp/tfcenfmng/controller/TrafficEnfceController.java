package com.moz.ates.traffic.policewebapp.tfcenfmng.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.moz.ates.traffic.common.component.enforcement.TrafficEnforcementIntegrationDto;
import com.moz.ates.traffic.common.entity.api.MojApiRequest;
import com.moz.ates.traffic.common.entity.common.CommonResponse;
import com.moz.ates.traffic.common.entity.common.MozCmCd;
import com.moz.ates.traffic.common.entity.driver.MozVioInfo;
import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.entity.equipment.MozTfcEnfFileInfo;
import com.moz.ates.traffic.common.entity.law.MozTfcLwFineInfo;
import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.common.entity.payment.MozPlPymntInfo;
import com.moz.ates.traffic.common.repository.driver.MozVioInfoRepository;
import com.moz.ates.traffic.common.repository.equipment.MozTfcEnfFileInfoRepository;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.policewebapp.tfcenfmng.service.TrafficEnfceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/tfcenfmng")
@RequiredArgsConstructor
public class TrafficEnfceController {

	private final TrafficEnfceService trafficEnfceService;
	
	private final FileUploadComponent fileUploadComponent;
	
	@Autowired
	MozVioInfoRepository mozVioInfoRepository;
	
	@Autowired
	MozTfcEnfFileInfoRepository mozTfcEnfFileInfoRepository;
	
	@GetMapping(value="/trafficEnfceRegPage")
	public String trafficEnfceRegPage(Model model, @ModelAttribute MojApiRequest mojApiRequest) {
		List<MozCmCd> dvrLcenTyList = trafficEnfceService.getDvrLcenTyCdList("DVR_LCEN_TY");
		List<MozCmCd> vehicleTypeCdList = trafficEnfceService.getDvrLcenTyCdList("VEHICLE_TYPE_CD");
		//법률 목록
		List<MozTfcLwInfo> trafficLawList = trafficEnfceService.getTrafficLawsListByNotNullFineInfo();
		//납부지 목록
		List<MozPlPymntInfo> placePaymentList = trafficEnfceService.getPlacePaymentList();
		
		if(mojApiRequest != null && !MozatesCommonUtils.isNull(mojApiRequest.getDatadenascimento())) {
			String birthDayFormat = MozatesCommonUtils.changeDateFormat(mojApiRequest.getDatadenascimento(), "yyyy-MM-dd'T'HH:mm:ss", "dd.MM.yyyy");
			mojApiRequest.setDatadenascimento(birthDayFormat);
			mojApiRequest.setVioAddr(MozatesCommonUtils.formatAddress(mojApiRequest.getDomicilio(), mojApiRequest.getProvincia(), mojApiRequest.getDistrito()));
		}
		model.addAttribute("apiDriverInfo",mojApiRequest);
		model.addAttribute("dvrLcenTyList",dvrLcenTyList);
		model.addAttribute("vehicleTypeCdList",vehicleTypeCdList);
		model.addAttribute("trafficLawList",trafficLawList);
		model.addAttribute("placePaymentList",placePaymentList);
		
		return "views/tfcenfmng/trafficEnfceRegPage";
	}

	@PostMapping(value="/lwFineInfo.ajax")
	@ResponseBody
	public CommonResponse<?> viewFineNtcInfo(Model model , @RequestParam("tfcLawId") String tfcLawId) {
		List<MozTfcLwFineInfo> lawFineInfoList = trafficEnfceService.getLawFineInfoList(tfcLawId);
		return CommonResponse.ResponseSuccess(HttpStatus.OK,"범칙금 정보 조회 성공", null, lawFineInfoList);
	}

	@PostMapping("/trafficEnfceRegPageRegister")
	@ResponseBody
	public CommonResponse<?> trafficEnfceRegPageRegister(
			TrafficEnforcementIntegrationDto trafficEnforcementIntegrationDto,
			@RequestParam(name = "files", required = false) MultipartFile[] files,
			@RequestParam(name = "polSignatureFile", required = false) MultipartFile polSignatureFile,
			@RequestParam(name = "vioSignatureFile", required = false) MultipartFile vioSignatureFile,
			Authentication authentication,
			RedirectAttributes redirectAttributes
	) {
		String tfcEnfId = "";
		try {
			//로그인정보에서 경찰 고유 ID 가져오기
			String polId = authentication.getName();
			
			if(polSignatureFile != null) { 
				trafficEnforcementIntegrationDto.setPoliceSignature(polSignatureFile);
			} else {
				return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Registration Failed(Not Found Police Signature)");
			}
			
			if(vioSignatureFile != null) { 
				trafficEnforcementIntegrationDto.setVioSignature(vioSignatureFile);
			}

			//위반 정보 등록
			tfcEnfId = trafficEnfceService.regTrafficEnfceInfo(trafficEnforcementIntegrationDto, polId, files);
		} catch (Exception ignored) {
			
		}
		return CommonResponse.ResponseSuccess(HttpStatus.OK, "Registration Success", null, tfcEnfId);
	}
	
	/**
	  * @Method Name : getEnfHistoryList
	  * @Date : 2024. 3. 14.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 이력 조회
	  * @param mozTfcEnfMaster
	  * @param model
	  * @return
	  */
	@GetMapping(value="/list")
	public String getEnfHistoryList(MozTfcEnfMaster mozTfcEnfMaster, Model model) {
		int page = mozTfcEnfMaster.getPage();
		int totalCnt = trafficEnfceService.getEnfCount(mozTfcEnfMaster);
		Pagination pagination = new Pagination(totalCnt, page, 5, 5);

		mozTfcEnfMaster.setLength(5);
		mozTfcEnfMaster.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("enfEnfMaster", mozTfcEnfMaster);
		model.addAttribute("pagination", pagination);
		model.addAttribute("enfList", trafficEnfceService.getTfcEnfList(mozTfcEnfMaster));
		
		return "views/tfcenfmng/trafficEnfceList";
	}

	@GetMapping(value="/trafficEnfceInfo/save.do")
	public String trafficEnfceInfoRegistPage(Model model) {
		
		return "views/tfcenfmng/trafficEnfceInfoRegist";
	}
	
	@GetMapping(value="/trafficEnfceCheckSearch/save.do")
	public String trafficEnfceCheckSearchPage(Model model) {
		
		return "views/tfcenfmng/trafficEnfceCheckSearch";
	}
	
	@GetMapping(value="/trafficEnfceregionInfo/save.do")
	public String trafficEnfceregionInfo(Model model) {
		
		return "views/tfcenfmng/trafficEnfceregionInfo";
	}
	
	@GetMapping(value="/trafficEnfceFileUpload/save.do")
	public String trafficEnfceFileUpload(Model model) {
		
		return "views/tfcenfmng/trafficEnfceFileUpload";
	}
	
	@GetMapping(value="/trafficEnfceAllInfo/save.do")
	public String trafficEnfceAllInfo(Model model) {
		
		return "views/tfcenfmng/trafficEnfceAllInfo";
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
	public String gethistoryDetail(HttpServletResponse response, @RequestParam(name="tfcEnfId", required = true) String tfcEnfId, Model model) {
		MozTfcEnfMaster enfDetail = trafficEnfceService.getTfcEnfDetail(tfcEnfId);

		model.addAttribute("enfDetail", enfDetail);
		model.addAttribute("enfFineList", trafficEnfceService.getAllTfcEnfFineInfo(tfcEnfId));
		return "views/tfcenfmng/trafficEnfceDetail";
	}
	
	/**
	  * @Method Name : signFileView
	  * @Date : 2024. 3. 19.
	  * @Author : IK.MOON
	  * @Method Brief : 위반자 서명 이미지 요청
	  * @param response
	  * @param vioId
	 * @throws IOException 
	  */
	@GetMapping("/sign/image")
	public void signFileView(HttpServletResponse response,
			@RequestParam(name = "vioId" ,required = true) String vioId
			) throws IOException   {
		MozVioInfo mozVioInfo = mozVioInfoRepository.findOneFileInfo(vioId);
		fileUploadComponent.imgView(response, mozVioInfo.getVioSignFilePath());
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
	@GetMapping("/tfcEnf/image")
	public void tfcEnfFileView(HttpServletResponse response,
			@RequestParam(name = "vioFileNo" ,required = true) String vioFileNo
			) throws IOException   {
		MozTfcEnfFileInfo tfcEnfFileInfo = mozTfcEnfFileInfoRepository.findOneByMozTfcEnfFileInfoByVioFileId(vioFileNo);
		fileUploadComponent.imgView(response, tfcEnfFileInfo.getFilePath());
	}
	
}
