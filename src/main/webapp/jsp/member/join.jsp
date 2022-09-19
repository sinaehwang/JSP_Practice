
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
	
	<script>
	
		var JoinForm__submitDone=false;//처음상태를 false로두면 조건절이 실행되지않았다가 제출시 true로 바뀌고 진행중멘트가 출력되도록 한다.
		function JoinForm__submit(form) {
			
			if(JoinForm__submitDone) {
				alert('가입처리 진행중입니다.');
				return;
			}
			
			form.loginId.value = form.loginId.value.trim();//공백입력시 제거하기 위해 trim()사용
			if(form.loginId.value.length == 0){
			alert('로그인아이디를 입력해주세요');
			form.loginId.focus();//focus는 하이라이팅효과
			return;
			}
			
			form.loginPw.value = form.loginPw.value.trim();
			if(form.loginPw.value.length == 0){
			alert('로그인비밀번호를 입력해주세요');
			form.loginPw.focus();
			return;
			}
			
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
			if(form.loginPwConfirm.value.length == 0){
			alert('비밀번호를 재확인해주세요');
			form.loginPwConfirm.focus();
			return;
			}
			
			if(form.loginPwConfirm.value != form.loginPw.value){
				alert('비밀번호가 일치하지 않습니다.');
				form.loginPw.focus();
				return;
				}
			
			form.name.value = form.name.value.trim();
			if(form.name.value.length == 0){
			alert('이름을 입력해주세요');
			form.name.focus();
			return;
			}
			
			form.submit();
			JoinForm__submitDone=true;
		}
	</script>
	<!-- return false 로 막아놓으면 submit 자동이 안됨(수동작업해야함)   -->
	<!-- a href = "https://www.naver.com" onclick==if(confirmfalse('이동')==false)return false; 출력창 생성시 취소를 누르면 false라서 출력만하고 페이지로 넘어가지않는다.     -->
	<form action="doJoin" method="post" onsubmit = "JoinForm__submit(this);return false;"> 
	<div>아이디: <input autocomplete="off" placeholder="아이디를 입력해주세요" name="loginId" type="text" /> </div> <!-- input 입력창만들어주기 -->
	<div>비밀번호: <input autocomplete="off" placeholder="비밀번호를 입력해주세요" name="loginPw" type="password"/> </div><!-- password는 비밀번호가 가려지게함 -->
	<div>비밀번호확인: <input autocomplete="off" placeholder="비밀번호를 재입력해주세요" name="loginPwConfirm" type="password"/> </div>
	<div>이름: <input autocomplete="off" placeholder="성명을 입력해주세요" name="name" type="text"/> </div> 
	<div>
	<button type="submit">가입</button>
	<button type="button"><a href="../home/main" >취소</a></button> <!-- 취소기능을 버튼으로 만들었을때에는 타입을 버튼으로 꼭 기재해야함(버튼기본타입은 제출형식이라서) -->
	</div>
	</form>
</body>
</html>