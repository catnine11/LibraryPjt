<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ^^ 맨위(1번째줄)는 jsp파일로 쓰기 위해 필요함 2:jstl 쓰기 위해 -->
    
<link href="<c:url value='/resources/css/include/nav.css'/>" rel="stylesheet" type="text/css">

	<nav>
		<div id="nav_wrap">
			<div class="menu">
				<ul>
					<li><a>로그인</a></li>
					<li><a href="<c:url value='/member/create'/>">회원가입</a></li>
				</ul>
			</div>
			<div class="search">
				<form>
					<input type="text"> <input type="button" value="검색">
				</form>
			</div>
		</div>
	</nav>