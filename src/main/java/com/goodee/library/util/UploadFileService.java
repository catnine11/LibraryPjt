package com.goodee.library.util;

import java.io.File;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service //service로 쓰겠다는 빈 등록, 의존성 주입해야함(컨트롤러에)
public class UploadFileService {
	
	private Logger logger = LoggerFactory.getLogger(UploadFileService.class);
	
	public String upload(MultipartFile file) {
		logger.info("[UploadFileService] upload();");
		
		//폼 xml에 파일 업로드 지원하는 모듈 있어야 함, Spring 폴더에 file-context.xml 파일 추가, web.xml에 file-context.xml 추가
		
		String result = "";
		// 파일을 서버에(우리 컴퓨터) 저장
		String fileOriName = file.getOriginalFilename();
		String fileExtension = fileOriName.substring(
				fileOriName.lastIndexOf("."), fileOriName.length()); 
				// 파일 원래이름의 제일 끝에서 .뒤에 파일명의 길이만큼(확장자) 잘라서 갖고옴   //.뒤에 확장자를 붙이기 위해서
		String uploadDir = "C:\\library\\upload\\";
		UUID uuid = UUID.randomUUID(); // 파일의 이름을 uuid(고유값, pk로 사용)로 바꿔서 이름 같아도 쓸수있도록
		// 파일명에 -이 들어가면 안됨 => -는 마이너스 역할이라.. 그래서 이걸 없애줌
		String uniqueName = uuid.toString().replaceAll("-", "");
		
		File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);
		 
		if(!saveFile.exists()) saveFile.mkdirs();   //한줄짜리 if문이라 {} 안 적음, 파일이 존재하지 않는다면 ~~
		
		try {
			file.transferTo(saveFile); //우리가 가져온 파일을 껍데기만 있는 파일정보에 넣어줌
			result = uniqueName + fileExtension; //파일 잘 넣어졌으면 바꾼 이름 + 확장자, 안넣어졌으면 초기값으로(이름 비어있는)
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
