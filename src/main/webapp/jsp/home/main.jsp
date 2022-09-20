<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%String MemberLogId = (String)request.getAttribute("MemberLogId"); %>


<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>메인페이지</title>
</head>
<body>
	<h1>메인페이지</h1>
	<div>
	<a href="../article/list">게시물 리스트</a>
	<a href="../member/join">회원가입</a>
	</div>
	
	<%if(MemberLogId!=null){ %>
	<div>
	<a href="../member/dologout">로그아웃</a>
	</div>
	<%}else { %>
	<div>
	<a href="../member/login">로그인</a>
	</div>
	<%} %>

	
	</div>	
	 
</body>
</html>