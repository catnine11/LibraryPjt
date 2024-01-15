package com.goodee.library.member;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

	private static final Logger logger = LoggerFactory.getLogger(MemberDao.class);
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder; //비밀번호 인코딩하는 거
	
	// 아이디 중복 검사
	public boolean isMemberCheck(String m_id) {
		logger.info("[MemberDao] isMemberCheck();");
		String sql = "SELECT COUNT(*) FROM tbl_member WHERE m_id = ?";
		int result = jdbcTemplate.queryForObject(sql, Integer.class, m_id);
		if(result>0) { //이미 이 멤버가 있다면
			return true;
		}else {
			return false;
		}
		
	}
	
	// 회원 정보 추가
	public int insertMember(MemberVo vo) {
		logger.info("[MemberDao] insertMember();");
		String sql = "INSERT INTO tbl_member(m_id, m_pw, m_name, m_gender,"
				+ " m_mail, m_phone, m_reg_date, m_mod_date) VALUES(?,?,?,?,?,?, NOW(), NOW())";
		List<String> args = new ArrayList<String>();
		//list는 순서 보장
		args.add(vo.getM_id());
		args.add(passwordEncoder.encode(vo.getM_pw()));
		args.add(vo.getM_name());
		args.add(vo.getM_gender());
		args.add(vo.getM_mail());
		args.add(vo.getM_phone());
		int result = -1;
		result = jdbcTemplate.update(sql, args.toArray());
		//result = 부분을 적어주지 않으면 결과가 항상 -1이기 때문에 실패가 뜨게 됨.(서비스의 memberService.createMember(vo) <=0가 항상 트루가 나오기 때문)
		//update 작업이 성공하면 update의 성공행의 개수가 뜸 => 성공시 0보다 큼
		
		return result;
	}
	
}
