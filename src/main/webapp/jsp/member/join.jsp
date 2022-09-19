
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입하기</title>
</head>
<body>
	<h1>회원가입</h1>
	<form action="doJoin" method="post"> <!-- action에는 전달할 곳을 적어준다. -->
	<div>아이디: <input autocomplete="off" placeholder="아이디를 입력해주세요" name="loginId" type="text" /> </div> <!-- input 입력창만들어주기 -->
	<div>비밀번호: <input autocomplete="off" placeholder="비밀번호를 입력해주세요" name="loginPw" type="text"/> </div>
	<div>이름: <input autocomplete="off" placeholder="성명을 입력해주세요" name="name" type="text"/> </div> 
	<button type="submit">가입하기</button>
	
	</form>
</body>
</html>