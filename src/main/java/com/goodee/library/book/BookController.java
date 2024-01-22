package com.goodee.library.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.library.util.UploadFileService;

@Controller //view가 요청한 컨트롤러를 받을 수 있게 함
@RequestMapping("/book")
public class BookController {

	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	UploadFileService uploadFileService; //의존성 주입(빈 등록 이후에)
	
	@Autowired
	BookService bookService;
	
	// 도서 등록 화면 이동
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createBookForm() {
		logger.info("[BookController] createBookForm();");
		
		return "book/create";
	}
	
	// 도서 등록 기능
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createBookConfirm(BookVo vo,
					@RequestParam("file") MultipartFile file) { // multipartfile로 파일을 갖고 옴
		logger.info("[BookController] createBookConfirm();");
		// 1. 도서 등록(UploadFilseService)
		String savedFildName = uploadFileService.upload(file);
		System.out.println(savedFildName);
		// 2. 파일 등록
		int result = -1;
		if(savedFildName != null && "".equals(savedFildName) == false) { //savedFildName가 비어있지 않는다면
			vo.setB_thumbnail(savedFildName);
//			int result = bookService.insertBook(vo);  // 도서등록
			result = bookService.createBookConfirm(vo);  // 도서등록
			//result를 내부에 넣으면 지역변수라 밖에서 쓸수 없음(결과화면 전환 판단기준으로 쓰고싶은데)
			//    => 결과를 if문 밖으로 빼고 초기화시켜줌
			
			// 1. BookService 빈 등록
			// 2. BookController 에 BookService 의존성 주입
			// 3. BookService 도서 등록 메소드(BookDao 에 부탁)
			// 4. BookDao 데이터베이스에 도서 등록(Mapper에 부탁)
			// 5. book_maper.xml 생성
			// 6. insert 구문 작성
			// 7. 테이블 tbl_book
					
		}
		
		// 3. 결과 화면 전환
		String nextPage = "";
//		if(bookService.createBookConfirm(vo) >0) {
		if(result >0) {
			nextPage = "book/create_success";
		}else {
			nextPage = "book/create_fail";
		}
		
		return nextPage;
	}
	
	
	
}
