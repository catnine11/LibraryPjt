<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link href="<c:url value='/resources/css/member/create.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<jsp:include page="../include/header.jsp"/> <!-- 패스를 디렉토리 기준으로 적어야 하기때문에 상위폴더 기준인 .. 으로 적어줌 -->
	<jsp:include page="../include/nav.jsp"/>
	
	<section>
		<div id="section_wrap">
			<div class="word">
				<h3>회원가입</h3>
			</div>
			<br>
			<div class="create_account_form">
				<form name="create_account_form" action="<c:url value='/member/create'/>" method="post">
		<!-- rest api 약속 : c: 기본, post, r:기본, get, u:기본/pk, post, d:기본/pk, delete -->
				<!-- 화면이동이랑 기능을 따로 만들기 위해 약속을 지키지 않았다 라고 말하면 ㄱㅊ -->
				
				<!-- map, requestparam, vo 로 받는 방법 중 vo로 받을거임 -->
					<input type="text" name="m_id" placeholder="아이디"> <br>
					<input type="password" name="m_pw" placeholder="비밀번호"> <br>
					<input type="password" name="m_pw_again" placeholder="비밀번호 확인">
					<br> <input type="text" name="m_name" placeholder="이름">
					<br> <select name="m_gender">
						<option value="">성별 선택</option>
						<option value="M">남성</option>
						<option value="W">여성</option>
					</select> <br> <input type="email" name="m_mail" placeholder="이메일"><br>
					<input type="text" name="m_phone" placeholder="전화번호"> <br>
					<input type="button" value="회원가입" onclick="createMemberForm();">
					<input type="reset" value="초기화">
				</form>
			</div>
			<div class="login">
				<a href="<c:url value='' />">로그인</a>
			</div>
		</div>
	</section>
	<script type="text/javascript">
		function createMemberForm() {
			let form = document.create_account_form;
			if (form.m_id.value == '') {
				alert('아이디를 입력하세요.');
				form.m_id.focus();
			} else if (form.m_pw.value == '') {
				alert('비밀번호를 입력하세요.');
				form.m_pw.focus();
			} else if (form.m_pw_again.value == '') {
				alert('비밀번호 확인을 입력하세요.');
				form.m_pw_again.focus();
			} else if (form.m_pw.value != form.m_pw_again.value) {
				alert('비밀번호가 일치하는지 확인하세요.');
				form.m_pw.focus();
			} else if (form.m_name.value == '') {
				alert('이름을 입력하세요.');
				form.m_name.focus();
			} else if (form.m_gender.value == '') {
				alert('성별을 선택하세요.');
				form.m_gender.focus();
			} else if (form.m_mail.value == '') {
				alert('메일 주소를 입력하세요.');
				form.m_mail.focus();
			} else if (form.m_phone.value == '') {
				alert('전화번호를 입력하세요.');
				form.m_phone.focus();
			} else {
				form.submit();
			}
		}
	</script>
</body>
</html>