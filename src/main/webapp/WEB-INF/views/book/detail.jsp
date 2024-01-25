<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서 상세 화면</title>
<link href="<c:url value='/resources/css/book/detail.css'/>" rel="stylesheet" type="text/css">
</head>
<body>
   <jsp:include page="../include/header.jsp"/>
   <jsp:include page="../include/nav.jsp"/>
   <section>   
      <div id="section_wrap">
         <div class="word">
            <h3>도서 상세 보기</h3>
         </div>
         <div class="book_detail">
            <ul>
               <li class="cover">
                  <img id="thumb" src="<c:url value="/libraryUploadImg/${bookVo.b_thumbnail}"/>"> 
                  <!-- 폴더 안만들고 이렇게 하는 이유? 다른 사람이 폴더 안에 이미지 있는걸 보고 해킹할 수 있음.. 그거 방지,
                  	스프링이 다이나믹한 파일이 아니라고 인식하도록 하기 위해 서블렛 컨텍스트에 코드추가해줌,, => url로 인식하는거 방지 -->
               </li>
               <li>
                  <table>
                     <tr>
                        <td>도서명</td>
                        <td>${bookVo.b_name}</td>
                     </tr>
                     <tr>
                        <td>저자</td>
                        <td>${bookVo.b_author}</td>
                     </tr>
                     <tr>
                        <td>발행처</td>
                        <td>${bookVo.b_publisher}</td>
                     </tr>
                     <tr>
                        <td>발행년도</td>
                        <td>${bookVo.b_publish_year}</td>
                     </tr>
                     <tr>
                        <td>등록일</td>
                        <td>${bookVo.b_reg_date}</td>
                     </tr>
                     <tr>
                        <td>수정일</td>
                        <td>${bookVo.b_mod_date}</td>
                     </tr>
                  </table>
               </li>
            </ul>
            
         </div>
         
         <div class="buttons">
            <a class="modify_book_button" href="/book/modify/${bookVo.b_no}">도서 수정</a>
            <a class="delete_book_button" onclick="deleteBook('${bookVo.b_no}');">도서 삭제</a>        
         </div>
      </div>
   </section>
	<script type="text/javascript">
		function deleteBook(bookNo){
			//1. 도서 삭제유무 확인
			let result = confirm('해당 도서를 정말 삭제하시겠습니까?'); // 확인창이 뜸, 결과가 true, false로 나옴
			//let : 자바스크립트 변수. 자바스크립트에서는 선언을 따로 하지 않기때문에(?) let, const, var 를 써서 변수 사용하는거 알려줌
			//자바스크립트 : 동적 자료형..
			//2. 도서 삭제 후 결과 alert로 알려주기
			if(result){
// 				fetch(주소, 옵션);
				fetch('/book/'+bookNo, {
					method : 'DELETE',
						//{안에 키:밸류 쓰면 객체} , headers: ajax에서 data와 비슷함.
					headers : { 
						'Content-Type':'application/json;charset=utf-8' 
					}//한글이 넘어갈 수도 있어서 인코딩해줌
				})
				.then(response => response.text())  //response.text() : 응답결과 중 text 받아옴
				.then(data => {
					if(data === '400'){
						alert('삭제성공');
						location.replace('/book');
					}else{
						alert('삭제 실패');
					}
				}) //앞에 수행한 결과를 매개변수로 받아올 수 있음
				.catch(error =>{
					console.log(error);
				});
			}
		}
		
		/** 아래 두개 같은 것.
			response => response.text()	//익명함수라 함수명 필요없음
			function 함수명(response){
				return response.text();
			//////////////////////////
			data => {}
			function 함수명(data){
				
			}
		}
		*/
		
	</script>
</body>
</html>