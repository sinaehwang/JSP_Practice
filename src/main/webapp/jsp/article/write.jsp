
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물작성하기</title>
</head>
<body>
	<h1>게시물작성</h1>
	<form action="doWrite" method="post"> <!-- action에는 전달할 곳을 적어준다. -->
	<div>제목: <input autocomplete="off" placeholder="제목을 입력해주세요" name="title" type="text" /> </div> <!-- input 입력창만들어주기 -->
	<div>내용: <textarea autocomplete="off" placeholder="내용을 입력해주세요" name="body"/></textarea> </div> <!-- textarea 닫는태그가 필요함 -->
	<button type="submit">작성</button>
	<div><a href="list">리스트 목록</a></div>
	</form>
</body>
</html>