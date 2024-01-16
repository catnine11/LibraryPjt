package com.goodee.library.member;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao dao;
	
	public int createMember(MemberVo vo) {
		logger.info("[MemberService] createMember()");
		int result = 0;
		if(dao.isMemberCheck(vo.getM_id())==false) {
			result = dao.insertMember(vo);
		}
		return result;
	}
	
	public MemberVo loginMember(MemberVo vo) {
		//vo 형태로 컨트롤러로부터 전달받아 올거임: 매개변수에 vo(id, 비밀번호)
		//리턴받는 vo는 모든 정보
		logger.info("[MemberService] loginMember();");
		MemberVo loginedMember = dao.selectMember(vo);
		return loginedMember;
	}
	
	public List<MemberVo> listupMember(){
		logger.info("[MemberService] listupMember();");
		return dao.selectMemberList();
	}
	
}
