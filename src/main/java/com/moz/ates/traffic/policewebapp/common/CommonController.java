package com.moz.ates.traffic.policewebapp.common;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moz.ates.traffic.common.component.FileUploadComponent;
import com.moz.ates.traffic.common.entity.accident.MozTfcAcdntFileInfo;
import com.moz.ates.traffic.common.entity.equipment.MozTfcEnfFileInfo;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntFileInfoRepository;
import com.moz.ates.traffic.common.repository.equipment.MozTfcEnfFileInfoRepository;

@Controller
@RequestMapping("/common")
public class CommonController {

	@Autowired
	MozTfcEnfFileInfoRepository tfcEnfFileInfoRepository;

	@Autowired
	MozTfcAcdntFileInfoRepository acdntFileInfoRepository;

	@Autowired
	FileUploadComponent fileUploadComponent;

	/**
	  * @Method Name : fileDowload
	  * @Date : 2024. 5. 14.
	  * @Author : IK.MOON
	  * @Method Brief : 파일 다운로드
	  * @param type
	  * @param fileId
	  * @param response
	  * @throws IOException
	  */
	@GetMapping("/file/{type}/download")
	public void fileDowload(@PathVariable(value = "type", required = true) String type,
			@RequestParam(value = "fileId", required = true) String fileId, HttpServletResponse response)
			throws IOException {

		if (type.equals("enf")) {
			MozTfcEnfFileInfo tfcEnfFileInfo = tfcEnfFileInfoRepository.findOneByMozTfcEnfFileInfoByVioFileId(fileId);
			fileUploadComponent.fileDownload(response
					, tfcEnfFileInfo.getFileNm()
					, tfcEnfFileInfo.getFileOrgNm()
					, tfcEnfFileInfo.getFilePath());
		} else if (type.equals("acdnt")) {
			MozTfcAcdntFileInfo tfcAcdntFileInfo = acdntFileInfoRepository
					.findOneMozTfcAcdntFileInfoByAcdntFileNo(fileId);
			fileUploadComponent.fileDownload(response
					, tfcAcdntFileInfo.getFileNm()
					, tfcAcdntFileInfo.getFileOriginNm()
					, tfcAcdntFileInfo.getFilePath());
		}

	}

}
