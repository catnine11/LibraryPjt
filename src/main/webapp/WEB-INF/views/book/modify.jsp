<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서 수정</title>
<link href="<c:url value='/resources/css/book/modify.css'/>" rel="stylesheet" type="text/css">
</head>
<body>
   <jsp:include page="../include/header.jsp"/>
   <jsp:include page="../include/nav.jsp"/>
   <section>
      <div id="section_wrap">
         <div class="word">
            <h3>도서 수정</h3>
         </div>
         <div class="modify_book_form">
            <form action="<c:url value='/book/modify/${bookVo.b_no}' />" name="modify_book_form" method="post" enctype="multipart/form-data">
               <input type="hidden" name="b_no" value="${bookVo.b_no}">
               <input type="text" name="b_name" value="${bookVo.b_name}" placeholder="INPUT BOOK NAME."> <br>
               <input type="text" name="b_author" value="${bookVo.b_author}" placeholder="INPUT BOOK AUTHOR."> <br>
               <input type="text" name="b_publisher" value="${bookVo.b_publisher}" placeholder="INPUT BOOK PUBLISHER."> <br>
               <input type="text" name="b_publish_year" value="${bookVo.b_publish_year}" placeholder="INPUT BOOK PUBLISH YEAR."> <br>
               <input type="file" name="file"><br> 
               <!-- 기존 파일이 안 뜨고 새로 선택된게 없기때문에 선택된 파일 없음 이라고 뜸 : 사용자는 헷갈릴 수 없음..
               visibility:hidden으로 숨긴 후에 라벨 안에 넣어줘서 파일첨부 눌렀을때만 뜨게 해주는게 좋음, 대신 파일명을 텍스트로 보여주기 -->
               <input type="button" value="수정" onclick="modifyBookForm();"> 
               <input type="reset" value="취소">
            </form>
         </div>
      </div>
   </section>
   <script type="text/javascript">
      function modifyBookForm() {
         let form = document.modify_book_form;
         if (form.b_name.value == '') {
            alert('도서 이름을 입력하세요.');
            form.b_name.focus();
         } else if (form.b_author.value == '') {
            alert('저자를 입력하세요.');
            form.b_author.focus();
         } else if (form.b_publisher.value == '') {
            alert('출판사를 입력하세요.');
            form.b_publisher.focus();
         } else if (form.b_publish_year.value == '') {
            alert('출판연도를 입력하세요.');
            form.b_publish_year.focus();
         } else {
            form.submit();
         }
      }
   </script>   
</body>
</html>