<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int currrentPage = (int)request.getAttribute("page");
int totalPage = (int)request.getAttribute("totalPage");
List<Map<String, Object>> articleRows = (List<Map<String, Object>>)request.getAttribute("articleRows");//getAttribute는 객
%>
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>게시물리스트구현하기</title>
</head>
<body>
	<div>
	<a href="https://www.naver.com" target="_blank">버튼1</a>
	<a href="/JSP_AM/article/list" target="_blank">버튼2</a>
	</div>
	
	<h1>게시물리스트(최종)</h1>
	
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
			<th>삭제</th>
			<th>수정</th>
		</tr>
		</thead>
	<tbody>
		<%for(Map<String,Object> articleRow:articleRows) {
		%>
		<tr>
			<td><%=articleRow.get("id") %></td>
			<td><%=articleRow.get("regDate") %></td>
			<td><a href="detail?id=<%=articleRow.get("id")%>"><%=articleRow.get("title") %></a></td>
			<td><a href="doDelete?id=<%=articleRow.get("id")%>">삭제</a></td>
			<td><a href="modify?id=<%=articleRow.get("id")%>">수정</a></td>
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