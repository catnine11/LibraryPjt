package com.goodee.library.member;

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
	
	
}
