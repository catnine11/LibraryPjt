package com.goodee.library.member;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MemberLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception{
		HttpSession session = req.getSession();
		if(session != null) { //세션에 정보가 존재하는 경우
			Object obj = session.getAttribute("loginMember"); //loginMember 확인해서 vo로 받아올거 아니라서 걍 오브젝트로 씀. vo써도 상관x
			if(obj != null) { //한줄짜리 if문은 중괄호 없어도 됨
				return true;
			}
		}
		resp.sendRedirect(req.getContextPath() + "/member/login"); //contextpath도 같이 써줘야 함
		return false;
	}
	
	//인터셉터 : 로그인 안한 상태에서 회원정보수정 url(/member/m_no 숫자) 하면 로그인 페이지로 자동 이동함.
	
}
