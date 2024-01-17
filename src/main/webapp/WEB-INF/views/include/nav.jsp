<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.goodee.library.member.MemberVo"%>
<!-- ^^ 맨위(1번째줄)는 jsp파일로 쓰기 위해 필요함 2:jstl 쓰기 위해 -->
    
<link href="<c:url value='/resources/css/include/nav.css'/>" rel="stylesheet" type="text/css">

	<nav>
		<div id="nav_wrap">
		<% 
			MemberVo loginedMember = (MemberVo)session.getAttribute("loginMember");
			if(loginedMember == null){
				//로그인 되어있지 않은 상태(로그인정보가 null이면) 로그인, 회원가입이 보임
		%>
			<div class="menu">
				<ul>
					<li><a href="<c:url value='/member/login'/>">로그인</a></li>
					<li><a href="<c:url value='/member/create'/>">회원가입</a></li>
				</ul>
			</div>
		<%
			} else{
				//로그인 되어있는 상태면 로그아웃, 계정수정, 회원목록이 보인다.
		%>
			<div class="menu">
				<ul>
					<li><a href="<c:url value='/member/logout'/>">로그아웃</a></li>
					<li>
						<a href="<c:url value='/member/${loginMember.m_no}'/>">계정수정</a>
<%-- 						<a href="<c:url value=''/>"> --%>
<%-- 							<c:out value="${empty loginMember ? 비어있음 : loginMember.m_no}"/> --%>
							<!-- JasperException : JSTL 문제임 -->
<!-- 						계정수정</a> -->
					</li>
					<li><a href="<c:url value='/member'/>">회원목록</a></li>
				</ul>
			</div>
			<div class="search">
				<form>
					<input type="text">
					<input type="button" value="검색">
				</form>
			</div>
		<%
			}
		%>
			
		</div>
	</nav>