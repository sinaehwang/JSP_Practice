<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Map<String, Object> articleRow = (Map<String, Object>)request.getAttribute("articleRow");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세페이지 구현하기</title>
</head>
<body>
	<h1><%=(int)articleRow.get("id") %>번 게시물 상세보기</h1>
	
	<%@ include file="../part/topBar.jspf" %> <!-- 파일 part를 불러오려면 mainpage서블렛에서 세션의 로그인 정보를 리스트 서블렛에서도 포함해줘야함 -->
	<p></p>
	<div>번호:<%=(int)articleRow.get("id") %></div>
	<div>날짜:<%=(LocalDateTime)articleRow.get("regDate") %></div>
	<div>제목:<%=(String)articleRow.get("title")%></div>
	<div>내용:<%=(String)articleRow.get("body")%></div>
	<div>작성자:<%=(String)articleRow.get("writer")%></div>
	<p></p>
	<a href="modify?id=${param.id }"><button style="margin:0px 10px;">수정</button></a><a href="doDelete?id=${param.id }"><button>삭제</button></a>
	<!--상세페이지에 수정이랑 삭제버튼 추가함  -->
	<p></p>
	<div><a href="list">리스트 목록</a></div>

</body>
</html>