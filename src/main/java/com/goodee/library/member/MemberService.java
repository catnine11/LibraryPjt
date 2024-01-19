package com.goodee.library.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao dao;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
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
		logger.info("[MemberService] findPasswordConfirm();");
		//1. 입력한 정보와 일치하는 사용자가 있는지 확인
		MemberVo selectedMember = dao.selectMemberOne(vo);
		int result = 0;
		if(selectedMember != null) {  //비어있지 않다면 => 회원인 사람
			//2. 새로운 비밀번호 생성
			String newPassword = createNewPassword();
			//3. 생성된 비밀번호 업데이트
			result = dao.updatePassword(vo.getM_id(),newPassword); //확인하는 로직이 없었다면 셀렉티드멤버에서 갖고 왔어야함
			if(result > 0) {
			//4. 메일 보내기
				sendNewPasswordByMail(vo.getM_mail(), newPassword);
				
			}
		}
		return result;
	}
	
	// 메일로 비밀번호 보내기
	private void sendNewPasswordByMail(String toMailAddr, String newPw) {
		logger.info("[MemberService] sendNewPasswordByMail();");
		
		final MimeMessagePreparator mime = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
										//mimeMessage : 객체 사용.. (고정), true: boolean html 태그로 쓸수있음, UTF: 인코딩
				mimeHelper.setTo(toMailAddr); //어디로 보낼건지
				mimeHelper.setSubject("[구디 도서관]새로운 비밀번호 안내입니다.");
				mimeHelper.setText("새 비밀번호 : "+newPw, true);
			}
		};
		javaMailSenderImpl.send(mime); 
	}
	
	// 비밀번호 생성
	private String createNewPassword() {
		logger.info("[MemberService] createNewPassword();");
		char[] chars = new char[] {
		         '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		         'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
		         'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
		         'u', 'v', 'w', 'x', 'y', 'z'
			         };
		StringBuffer sb = new StringBuffer();
		SecureRandom sr = new SecureRandom();
		sr.setSeed(new Date().getTime());
		int index = 0;
		int length = chars.length; //배열의 길이  //랜덤이 쓰는 범주는 한 번만 쓰면 됨. 그래서 반복문 밖으로 뺌
		for(int i = 0; i<8; i++) { //초기식, 조건식(8번 반복), 증감식
			index = sr.nextInt(length);
			if(index % 2 == 0) {
//				sb.append(chars[index]);
//				sb.append(String.valueOf(chars[index]));  //char는 대문자로 바꿀 수 없기때문에 char를 string으로 바꿔서 넣어야됨
				sb.append(String.valueOf(chars[index]).toUpperCase()); //짝수는 대문자로 바꿔서
			}else {
				sb.append(String.valueOf(chars[index]).toLowerCase()); //홀수는 소문자
			}
		}
		return sb.toString(); //스트링 버퍼에 있는것을 스트링으로 바꿔서 리턴
	}
	
	
}
