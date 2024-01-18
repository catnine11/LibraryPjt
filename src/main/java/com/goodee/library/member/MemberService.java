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
	
	public int modifyMember(MemberVo vo) {
		logger.info("[MemberService] modifyMember();");
		return dao.updateMember(vo);
	}
	
	//회원 단일 정보 조회
	public MemberVo getLoginedMemberVo(int m_no) {
				// 정수값인 m_no 를 받아와서 해당 회원의 정보를 확인하고 MemberVo 객체(정보를 담아서 전해줄 껍데기)로 정보를 넘겨줌
		logger.info("[MemberService] getLoginedMemberVo();");
//		return dao.getmodifyMember(m_no); //내가 ~ 
		return dao.selectMemberOne(m_no); //쌤 수업
	}
	
	
	public int findPasswordConfirm(MemberVo vo) {
		//1. 입력한 정보와 일치하는 사용자가 있는지 확인
		MemberVo selectedMember = dao.selectMemberOne(vo);
		int result = 0;
		if(selectedMember != null) {  //비어있지 않다면 => 회원인 사람
			//2. 새로운 비밀번호 생성
			
			//3. 생성된 비밀번호 업데이트
			
			//4. 메일 보내기
			
		}
		return result;
	}
	
}
