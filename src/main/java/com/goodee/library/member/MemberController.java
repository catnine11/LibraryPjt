package com.goodee.library.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/member")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	MemberService memberService;
	
	// 페이지 이동 -> 기능
	// 회원가입 화면 이동(페이지 이동)
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public String openMemberCreate() {
		logger.info("[MemberController] openMemberCreate();");
		// WEB-INF/views/member/create.jsp
		return "member/create"; // member 폴더 밑에 create
	}
	
	// 회원가입 기능 수행
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createMember(MemberVo vo) {
		logger.info("[MemberController] createMember();");
		String nextPage = "member/create_success";
		if(memberService.createMember(vo) <=0){
			nextPage = "member/create_fail";
		}
		memberService.createMember(vo);
		return nextPage;
	}
	
}
