<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서상세화면</title>
<%-- <link href="<c:url value='/resources/css/book/detail.css'/>" rel="stylesheet" type="text/css"> --%>
</head>
<body>
   <jsp:include page="../include/header.jsp"/>
   <jsp:include page="../include/nav.jsp"/>   
   <section>
      <div id="section_wrap">
         <div class="word">
         </div><br>
         <div class="book_One">
         ${bookVo}
            <table>
               <thead>
                  <tr>
                  	<th>썸네일</th>
                     <th>도서명</th>
                     <th>저자</th>
                     <th>발행처</th>
                     <th>발행연도</th>
                  </tr>
               </thead>
               <tbody>
                     <tr>
                     	<td>
                     		<img src="${bookVo.b_thumbnail}">
                     	 </td>
                        <td>
                           ${bookVo.b_name}
                        </td>
                        <td>${bookVo.b_author}</td>
                        <td>${bookVo.b_publisher}</td>
                        <td>${bookVo.b_publish_year}</td>
                     </tr>
               </tbody>
            </table>
         </div>
      </div>
   </section>   
</body>
</html>