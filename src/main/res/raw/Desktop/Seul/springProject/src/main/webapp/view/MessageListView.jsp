<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<%
	request.setCharacterEncoding("UTF-8");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록 메시지 목록</title>

</head>
<body>
	<form action="${contextPath }/message/writeMessage.do" method ="post" id="writeMessage">
		이름 : <input type="text" name = "guestName"><br>
		암호 : <input type="password" name = "password"><br>
		메시지 :<br>
		 <textarea name="message" cols="30" rows="3"  ></textarea> <br>
		<input type="submit" value="메시지 남기기"  onclick="alert('방명록에 메시지를 남겼습니다')">
	</form>
<hr>

	<c:if test="${pageVO.isEmpty() }">
		등록된 메시지가 없습니다.
	</c:if>
	<c:if test="${!pageVO.isEmpty() }">
		<table border="1">
				<c:forEach var="message" items="${messageList }">
					<tr>
						<td>
							메시지 번호 : ${message.id } <br>
							손님 이름 : ${message.guestName } <br>
							메시지 : ${message.message }<br>
							<a href="${contextPath }/view/confirmDeletion.jsp?id=${message.id }">[삭제하기]</a>
						</td>
					</tr>
			</c:forEach>	
		</table>
	 	<c:forEach var="pageNum" begin="1" end="${pageVO.pageTotalCount }">
			<a href="${contextPath }/message/listMessages.do?pageNum=${pageNum }">[${pageNum }]</a>
		</c:forEach>
	</c:if>
</body>
</html>