
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Map<String, Object> articleRow = (Map<String, Object>)request.getAttribute("articleRow");
%>
<%String MemberLogId = (String)request.getAttribute("MemberLogId"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물수정하기</title>
</head>
<body>
	<h1><%=articleRow.get("id")%>번 게시글 수정</h1>

	<form action="doModify" method="post"> <!-- action에는 전달할 곳을 적어준다. -->
	<input type="hidden" name="id" value="${param.id}" />
	<div>번호:<%=(int)articleRow.get("id") %></div>
	<div>날짜:<%=(LocalDateTime)articleRow.get("regDate")%></div>
	<div>제목: <input autocomplete="off" placeholder="제목을 입력해주세요" name="title" type="text" value="<%=(String)articleRow.get("title")%>" /></div> <!-- input 입력창만들어주기 -->
	<div>내용: <textarea autocomplete="off" placeholder="내용을 입력해주세요" name="body" type="text"/><%=(String)articleRow.get("body")%></textarea></div><!-- textarea 닫는태그가 필요함 -->
	<button type="submit" style="border:solid 2px" >수정</button>
	<div><a href="list">리스트 목록</a></div>
	</form>
</body>
</html>