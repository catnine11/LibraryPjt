package com.goodee.library.book;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	// 도서 목록 조회 기능(검색)
	@RequestMapping(method = RequestMethod.GET) //레스트api: 목록조회는 기본 url로 함.. 이럴때는 value 안 적어도 됨
//	public String selectBookList(Model model, @RequestParam(required = false) String b_name) {
	public String selectBookList(Model model, BookVo vo) {
	//목록을 보여줄때 스트링으로 어떤거 보낼지 하고 model.addAttribute로 어떤 정보 전달할지
	// requestParam : 어떤 이름으로 부를지, required=false : 필수 입력값은 아님(true가 기본값)
		logger.info("[BookController] selectBookList();");
		//1. 목록 정보 조회(DB)
//		List<BookVo> bookVos = bookService.selectBookList(b_name);
		
		vo.setTotalCount(bookService.selectBookCount(vo.getB_name()));
//		List<BookVo> bookVos = bookService.selectBookList(vo.getB_name());
		List<BookVo> bookVos = bookService.selectBookList(vo);
		//2. 화면 전환 + 정보 전달
		model.addAttribute("bookVos", bookVos); //앞 : 어떤 이름으로 전달할지, 뒤: 어떤거 전달할지
		model.addAttribute("pagingVo", vo);
		
		return "book/listup";
	}
	
	// 도서 상세화면 이동
	// 메소드 명 -> bookDetail
	// 정수형 b_no를 url을 통해서 받아오기  => 스프링 문법으로 /{}
	@RequestMapping(value = "/{b_no}", method = RequestMethod.GET)
	public String bookDetail(@PathVariable int b_no, Model model) { //url 안의 정보를 어떤 타입, 어떤 이름으로 쓸건지(부를건지)
		logger.info("[BookController] bookDetail();");
		//1. 도서 한 권의 정보 조회
		BookVo vo = bookService.bookDetail(b_no);
		//2. 화면 전환 + 정보 전달
		model.addAttribute("bookVo", vo);
		
		return "book/detail";
	}
	
	// 도서 수정 화면 이동
	@RequestMapping(value = "/modify/{b_no}", method = RequestMethod.GET)
	public String modifyBookForm(@PathVariable int b_no, Model model) { //변경 전 정보와 변경 후 정보를 보여주기 때문에 정보 전달 같이 해야함
		logger.info("[BookController] modifyBookForm();");
		//1. 기존 정보 조회
		BookVo vo = bookService.bookDetail(b_no); // 도서상세화면 이동창을 이미 만들었기 때문에 그대로 쓰면 됨?
				// 화면이 아니라 수정 전의 정보를 갖고오고 싶은거임. 컨트롤러가 하고싶은 일이 다르니까 서비스를 따로 만들고 dao 똑같이 하는게 정석적
				// 상세화면과 수정에서 보는 정보가 다른(가져오는 정보가 다를때)는 셀렉트컬럼에서 쿼리를 나눌때는 서비스를 나눠주는 것이 좋음
		//2. 화면 전환 + 정보 전달
		model.addAttribute("bookVo", vo);
		
		return "book/modify";
	}
	
	// 도서 수정 기능
	@RequestMapping(value = "/modify/{b_no}" ,method = RequestMethod.POST)
//	public String modifyBookConfirm(BookVo vo, @RequestParam("file") MultipartFile file, @PathVariable int b_no) { //내가
	public String modifyBookConfirm(BookVo vo, @RequestParam("file") MultipartFile file) {
		//file을 받을때는 requestparam써야하고 인코딩타입 적어야함(사용자가 파일 수정할수도 있음
		logger.info("[BookController] modifyBookConfirm();");
		// 1. 만약에 새로운 파일O -> 파일 업로드
		if(file.getOriginalFilename().equals("") == false) { //파일이 있으면 파일이름 있음, getOriginalFilename : 스트링쓰는게 가장 부하 적음
			String savedFileName = uploadFileService.upload(file);
			if(savedFileName != null) {
				vo.setB_thumbnail(savedFileName);
			}
		}
		// 2. 도서 정보 수정
		int result = bookService.modifyConfirm(vo);
		// (1) BookService에 modifyConfirm 메소드 생성
		// (2) BookDao 에 updatBook 메소드 생성
		// (3) BookService의modifyConfirm이 BookDao의 updateBook으로부터 int(update 수행결과)를 전달
		// (4) book_mapper에 updateBook 쿼리 생성
		// -> 파라미터가 BookVo
		// -> tbl_book을 UPDATE
		// -> b_name, b_author, b_publisher, b_publish_year, b_mod_date
		// -> 만약에 b_thumbnail이 null이 아니면서 빈 스트링이 아니라면 b_thumbnail도 수정
		
		///// 디버그할때 mod_date는 null : modify에서 안보냈기때문에
		
		// 3. 결과 화면 이동
		if(result>0) {
			return "book/modify_success";
		}else {
			return "book/modify_fail";
		}
		
		//(내가)
//		if(result >0) {
////			return "book/detail"; //url이 아니라 detail.jsp로
//			return "redirect:/book/detail/" +b_no; //redirect 남발하는게 좋지 않기때문에 정보를 새로고침 하고싶으면 ajax쓰던가 해야함
//		}else {
//			return "book/modify";
//		}
		
	}
	
	// 도서 삭제 기능
	@RequestMapping(value = "/{b_no}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBookConfirm(@PathVariable int b_no){
		logger.info("[BookController] deleteBookConfirm();");
		// 실패 상황 가정(default)
		String result = "200";
		if(bookService.deleteBook(b_no) >0) {
			result = "400";
		}
		return ResponseEntity.ok(result); //ResponseEntity 상태를 보여주기 위한 것, @RequestBody 해도 됨
	}
	
}
