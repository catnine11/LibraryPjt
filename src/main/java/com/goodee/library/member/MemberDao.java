package com.goodee.library.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

	private static final Logger logger = LoggerFactory.getLogger(MemberDao.class);
	
	
//	@Autowired
//	JdbcTemplate jdbcTemplate;

	@Autowired
	private SqlSession sqlSession; //
	
	@Autowired
	PasswordEncoder passwordEncoder; //비밀번호 인코딩하는 거
	
	// 아이디 중복 검사 -jdbc 템플릿
//	public boolean isMemberCheck(String m_id) {
//		logger.info("[MemberDao] isMemberCheck();");
//		String sql = "SELECT COUNT(*) FROM tbl_member WHERE m_id = ?";
//		int result = jdbcTemplate.queryForObject(sql, Integer.class, m_id);
//		if(result>0) { //이미 이 멤버가 있다면
//			return true;
//		}else {
//			return false;
//		}
//	}
	
	private final String namespace = "com.goodee.library.member.MemberDao.";
	
	//아이디 중복검사 - mybatis
	public boolean isMemberCheck(String m_id) {
		logger.info("[MemberDao] isMemberCheck();");
		int result = sqlSession.selectOne(namespace+"isMemberCheck", m_id);
		//sqlsession의 selectOne 한 것을 mapper가 받아옴
		if(result > 0) return true;
		else return false; //{}가 한 줄 짜리면 따로 안 적어도 괜찮음
	}
	
	//회원 정보 추가 - mybatis
	public int insertMember(MemberVo vo) {
		logger.info("[MemberDao] insertMember();");
		
		vo.setM_pw(passwordEncoder.encode(vo.getM_pw())); //vo의 pw를 꺼내서 암호화 한 뒤 다시 vo에 넣어줌
		//위의 암호화 과정을 풀어서 쓰면 아래와 같음
//		String rawPw = vo.getM_pw();
//		String encodePw = passwordEncoder.encode(rawPw);
//		vo.setM_pw(encodePw);
//		
		int result = -1;
		result = sqlSession.insert(namespace+"insertMember", vo);
		return result;
	}
	
	
	// 회원 정보 추가 -jdbcTemplate
//	public int insertMember(MemberVo vo) {
//		logger.info("[MemberDao] insertMember();");
//		String sql = "INSERT INTO tbl_member(m_id, m_pw, m_name, m_gender,"
//				+ " m_mail, m_phone, m_reg_date, m_mod_date) VALUES(?,?,?,?,?,?, NOW(), NOW())";
//		List<String> args = new ArrayList<String>();
//		//list는 순서 보장
//		args.add(vo.getM_id());
//		args.add(passwordEncoder.encode(vo.getM_pw())); //List<String>에 vo를 넣어줬기때문에 pw만 꺼내서 암호화할 수 있었음
//		args.add(vo.getM_name());
//		args.add(vo.getM_gender());
//		args.add(vo.getM_mail());
//		args.add(vo.getM_phone());
//		int result = -1;
//		result = jdbcTemplate.update(sql, args.toArray());
//		//result = 부분을 적어주지 않으면 결과가 항상 -1이기 때문에 실패가 뜨게 됨.(서비스의 memberService.createMember(vo) <=0가 항상 트루가 나오기 때문)
//		//update 작업이 성공하면 update의 성공행의 개수가 뜸 => 성공시 0보다 큼
//		
//		return result;
//	}
	
	// 로그인(회원정보 조회 및 비밀번호 확인) - Mybatis
	public MemberVo selectMember(MemberVo vo) {
		logger.info("[MemberDao] selectMember();");
		MemberVo resultVo = new MemberVo();
		resultVo = sqlSession.selectOne(namespace+"selectMember", vo.getM_id());
			// id가 틀리면 resultvo가 비어있기때문에 resultVo.getM_pw()가 비어서 NullPointerException이 뜸
			//=> 막아주자!! => if(resultVo != null) { 안에 넣어주기~
		if(resultVo != null) {
			if(passwordEncoder.matches(vo.getM_pw(), resultVo.getM_pw())== false) {
				resultVo = null;
			}
		}
//		혹은
//		if(resultVo != null && passwordEncoder.matches(vo.getM_pw(), resultVo.getM_pw())== false) {
//			resultVo = null;
//		}
		return resultVo;
	}
	
	// 로그인 -jdbcTemplate
//	public MemberVo selectMember(MemberVo vo) {
//		//id, pw를 vo로 전달받아서 vo 형태로 모든 정보 전해줄거임
//		logger.info("[MemberDao] selectMember();");
//		
//		String sql = "SELECT * FROM tbl_member WHERE m_id = ?"; 
//		//jdbcTemplate은 select * 하면 무조건 리스트 형태로 받음 => List<MemberVo>로 받게 됨
//		//(jdbcTemplate은 하나의 정보만 보낼 수 없고 리스트처럼 여러개를 보내야 함)
//		List<MemberVo> memberVos = new ArrayList<MemberVo>();
//		try {
//			memberVos = jdbcTemplate.query(sql, new RowMapper<MemberVo>() {
//				@Override  //상속받아서 쓸거임
//				public MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException{
//					MemberVo memberVo = new MemberVo();
//					memberVo.setM_no(rs.getInt("m_no"));
//					memberVo.setM_id(rs.getString("m_id"));
//					memberVo.setM_pw(rs.getString("m_pw"));
//					memberVo.setM_name(rs.getString("m_name"));
//					memberVo.setM_gender(rs.getString("m_gender"));
//					memberVo.setM_mail(rs.getString("m_mail"));
//					memberVo.setM_phone(rs.getString("m_phone"));
//					memberVo.setM_reg_date(rs.getString("m_reg_date"));
//					memberVo.setM_mod_date(rs.getString("m_mod_date"));
//					return memberVo;
//				}
//			}, vo.getM_id()); //id가 일치하는 정보를 받아옴
//			if(passwordEncoder.matches(vo.getM_pw(), memberVos.get(0).getM_pw())==false) {
//				// matches(앞: 암호화x, 뒤: 암호화된 비번)
//				memberVos.clear();  //비번이 일치하지 않으면 비밃ㄴ호 지움
//			}
//			
//			//RowMapper: row가 여러개인 것을 받아올건데
//			//MemberVo: row 하나가 MemberVo임
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return memberVos.size() > 0? memberVos.get(0) : null;
//	}
	
	// 회원목록 조회 - mybatis
	public List<MemberVo> selectMemberList(){
		logger.info("[MemberDao] selectMemberList();");
		List<MemberVo> resultList = new ArrayList<MemberVo>();
		resultList = sqlSession.selectList(namespace+"selectMemberList");
		return resultList;
	}
	
	// 회원목록 조회 -jdbcTemplate
//	public List<MemberVo> selectMemberList(){
//		logger.info("[MemberDao] selectMemberList();");
//		
//		//jdbcTemplate은 쿼리를 스트링으로 적고 where에 뭐가 들어갈지, ..과 결과를 따로 parsing 해줘야함 
//		// => 불편함 => myBatis 쓸거임(프레임워크)
//		
//		String sql = "SELECT * FROM tbl_member";
//		List<MemberVo> memberVos = new ArrayList<MemberVo>();
//			memberVos = jdbcTemplate.query(sql, new RowMapper<MemberVo>() {
//				@Override  //상속받아서 쓸거임
//				public MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException{
//					MemberVo memberVo = new MemberVo();
//					memberVo.setM_no(rs.getInt("m_no"));
//					memberVo.setM_id(rs.getString("m_id"));
//					memberVo.setM_pw(rs.getString("m_pw"));
//					memberVo.setM_name(rs.getString("m_name"));
//					memberVo.setM_gender(rs.getString("m_gender"));
//					memberVo.setM_mail(rs.getString("m_mail"));
//					memberVo.setM_phone(rs.getString("m_phone"));
//					memberVo.setM_reg_date(rs.getString("m_reg_date"));
//					memberVo.setM_mod_date(rs.getString("m_mod_date"));
//					return memberVo;
//				}
//			});
//		return memberVos;
//	}
	
	// 회원정보 수정
	public int updateMember(MemberVo vo) {
		logger.info("[MemberDao] updateMember();");
		int result = sqlSession.update(namespace+"updateMember", vo);
		return result;
	}
	
	// 회원 단일 정보 조회
//	public MemberVo getmodifyMember(int m_no) {
//		logger.info("[MemberDao] getmodifyMember();");
//		MemberVo resultVo = new MemberVo();
//		resultVo = sqlSession.selectOne(namespace+"getmodifyMember", m_no);
	public MemberVo selectMemberOne(int m_no) { //int m_no 기준으로 받아온 값을 vo 객체에 담아서 넘겨줌
		logger.info("[MemberDao] selectMemberOne();");
		MemberVo resultVo = new MemberVo();
		resultVo = sqlSession.selectOne(namespace+"selectMemberOne", m_no);
		return resultVo;
	}
	
	public MemberVo selectMemberOne(MemberVo vo) {  //id, mail, 이름을 기준으로 정보(회원 한명)를 조회하고 싶지만
//								필요한 정보가 여럿이라 vo를 파라미터 로 써줌 => 위에와 메소드는 같지만 매개변수만 다를때 : 오버로드
		logger.info("[MemberDao] selectMemberOne();");
		MemberVo memberVo = sqlSession.selectOne(namespace+"selectMemberForPassword", vo);
		return memberVo;
	}
	
	
}
