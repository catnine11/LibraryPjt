<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서관리시스템</title>
<link href="<c:url value='/resources/css/home.css'/>" rel="stylesheet"	type="text/css">
</head>
<body>
	<jsp:include page="include/header.jsp"/>	<!-- 공통화 : header.jsp 의 내용이 이 안에 들어오게 됨--> 
	<jsp:include page="include/nav.jsp"/>
	<!-- jsp:include는 java에서 하는거라 안에 jstl을 따로 넣어줘야 하고 <//%@ include file=""%>는 html이 하는거라 jstl 따로 안 넣어줘도 됨 -->
	
	<section>
		<div id="section_wrap">
			<div class="word">
				<h3>HOME</h3>
			</div>
		</div>
	</section>
</body>
</html>
