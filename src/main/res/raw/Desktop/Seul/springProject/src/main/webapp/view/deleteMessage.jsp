<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:choose>
		<c:when test="${!invalidPassword}">
		메시지를 삭제하였습니다
		</c:when>
		<c:otherwise>
		입력한 암호가 올바르지 않습니다. 암호를 확인해주세요
		</c:otherwise>
	</c:choose>
	<br>

	<a href="${contextPath }/message/listMessages.do">[목록보기]</a>

</body>
</html>