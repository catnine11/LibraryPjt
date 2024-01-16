package com.goodee.library.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	
	// 로그인 화면 이동
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String openLoginForm() {
		logger.info("[MemberController] openLoginForm();");
		// views - member - login_form.jsp 이동
		return "member/login_form";
	}
	
	// 로그인 기능
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginMember(MemberVo vo, HttpSession session) { //session을 매개변수에 넣어줌(로그인 정보 저장 위해서)
		//암호화되지 않은 비번 전달 기준으로 암호화 만들어줌
		logger.info("[MemberController] loginMember();");
		String nextPage = "member/login_success";
		MemberVo loginedMember = memberService.loginMember(vo);
		if(loginedMember == null) {
			nextPage = "member/login_fail";
		}else {
			//null이 아닐 때(로그인 되어있을 때) 로그인 정보를 세션에 저장함
			session.setAttribute("loginMember", loginedMember);
			session.setMaxInactiveInterval(60*30); //()안의 int값을 초 단위로 적어줘야 함. 60초*30(분)
		}
		return nextPage;
	}
	
	// 로그아웃 기능
	@RequestMapping(value = "/logout", method = RequestMethod.GET) //a태그로 보낸것은 get으로 받는다
	public String logoutMember(HttpSession session) {
		logger.info("[MemberController] logoutMember();");
		session.invalidate();
		return "redirect:/";
	}
	
	// 회원목록 이동 - ModelAndView (1)
	// 위에 @RequestMapping("/member")를 해놨기 때문에 url을 따로 적지 않으면 member로 자동 이동되므로 적을 필요 없음
//	@RequestMapping(method = RequestMethod.GET)
//	public ModelAndView listupMember() {
//		//String 화면이동정보, Model 정보만, ModelAndView 화면과 정보 둘 다 (viewResolver에게) 전달해줄때
//		logger.info("[MemberController] listupMember();");
//		// 1. 목록 정보
//		List<MemberVo> memberVos = memberService.listupMember();
//		// 2. 목록 전달
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("memberVos", memberVos);
//		// 3. 뷰 선택
//		mav.setViewName("member/listup");
//		return mav;
//	}
	
	// 회원목록 이동 - ModelAndView (2)
//	@RequestMapping(method = RequestMethod.GET)
//	public ModelAndView listupMember(ModelAndView mav) {
//		//String 화면이동정보, Model 정보만, ModelAndView 화면과 정보 둘 다 (viewResolver에게) 전달해줄때
//		logger.info("[MemberController] listupMember();");
//		// 1. 목록 정보
//		List<MemberVo> memberVos = memberService.listupMember();
//		// 2. 목록 전달
//		mav.addObject("memberVos", memberVos);
//		// 3. 뷰 선택
//		mav.setViewName("member/listup");
//		return mav;
//	}
	
	// 회원목록 이동 - Model(1)
	@RequestMapping(method = RequestMethod.GET)
	public String listupMember(Model model) {
		//String 화면이동정보, Model 정보만, ModelAndView 화면과 정보 둘 다 (viewResolver에게) 전달해줄때
		logger.info("[MemberController] listupMember();");
		// 1. 목록 정보
		List<MemberVo> memberVos = memberService.listupMember();
		// 2. 목록 전달
		model.addAttribute("memberVos", memberVos);
		return "member/listup";
	}
	
}
