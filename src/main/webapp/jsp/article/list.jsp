<%@page import="com.KoreaIT.java.am.dto.Article"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int currrentPage = (int)request.getAttribute("page");
int totalPage = (int)request.getAttribute("totalPage");
List<Article> articles = (List<Article>)request.getAttribute("articleRows");
%>




<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>게시물리스트구현하기</title>
</head>
<body>
	

	<h1>게시물리스트(최종)</h1>
<%@ include file="../part/topBar.jspf" %> <!-- 파일 part를 불러오려면 mainpage서블렛에서 세션의 로그인 정보를 리스트 서블렛에서도 포함해줘야함 -->
	<p></p>
	<div><a href="write">글쓰기</a></div>
	
	<table border = "1" bordercolor="green">
		<colgroup>
			<col width="50"/>
			<col  width="200" />
			<col  width="300" />
			<col />
		</colgroup>
	<thead>
		<tr>
			<th>번호</th>
			<th>날짜</th>
			<th>제목</th>
			<th>작성자</th>
			<th>삭제</th>
			<th>수정</th>
		</tr>
		</thead>
	<tbody>
		<%for(Article article:articles) { //맵타입으로 만든 articles를 Article 객체로 다시만들면 호출사용이 쉬워짐
		%>
		<tr>
			<td><%=article.id %></td>
			<td><%=article.regDate %></td>
			<td><a href="detail?id=<%=article.id%>"><%=article.title %></a></td>
			<td><%=article.writer %></td>
			<td><a href="doDelete?id=<%=article.id%>">삭제</a></td>
			<td><a href="modify?id=<%=article.id%>">수정</a></td>
		</tr>
		<%} %>
		</tbody>
		
	</table>
	
	<style type = "text/css">
		.page > a.red{
			color:red;
		}
	</style>
	
	<div class="page"><!-- 페이지버튼 구현하기 -->
	
	<%if(currrentPage>1) {%>
	<a href="list?page=1">이전글</a>
	<%} %>
	<!--a[href="list?page=$"]{$}*10  -->
	
	<!--현재페이지가 파라이터 page=? 입력값과 일치할때만 컬러를 red로준다 => a.red -->
	<%
	int pageMenuSize = 4;// 현재페이지 기준 처음과 끝에 보여줄 변수 필요
	int start=currrentPage-pageMenuSize;
	if(start<1) {
		start=1;
	}
	int end=currrentPage+pageMenuSize;
	if(end>totalPage) {
		end=totalPage;
	}
	for (int i=start; i<=end; i++) {%>
		<a class="<%=currrentPage == i? "red" : "" %>" href="list?page=<%=i%>"><%=i%></a>
	<%} %>
	
	<%if(currrentPage<totalPage) {%>
	<a href="list?page=<%=totalPage%>">다음글</a>
	<%} %>
	</div>
	 
</body>
</html>