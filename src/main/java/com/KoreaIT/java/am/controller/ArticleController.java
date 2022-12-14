package com.KoreaIT.java.am.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import com.KoreaIT.java.am.dto.Article;
import com.KoreaIT.java.am.service.ArticleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArticleController {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ArticleService articleservice;

	public ArticleController(Connection conn, HttpServletRequest request, HttpServletResponse response) {
		
		this.request = request;
		this.response = response;
		
		articleservice = new ArticleService(conn);
		
	}

	public void actionList() throws ServletException, IOException {
		
		int page =1;
		
		if(request.getParameter("page") != null && request.getParameter("page").length() !=0 ) {
			try {//try로 감싸면 page=입력받을시 숫자가 아닌 문자로 받게되는 경우를 예외로 처리해주기 위해서//문자로 입력받아도 페이지가 유지됨
				page = Integer.parseInt(request.getParameter("page"));//request로 받은 파라미터 page를 숫자화시킴
			} catch(NumberFormatException e){
				
			}
		}
		
		int totalPage = articleservice.getTotalPage();//실수연산을먼저해서 나머지를 발생시킨후 올림을 실행한후에 정수화를 해야함
		
		List<Article> articleRows =  articleservice.getarticleRows(page);
		//List<Map<String,Object>>articleRows = articleservice.getarticleRows(page);//db에 있는 메소드들이 다 static이기 때문에 객체생성할 필요가 없어짐
		
		/*
		 * for(int i=0; i<articleRows.size(); i++) { Map<String,Object>articleRow =
		 * articleRows.get(i); int id = (int) articleRow.get("id"); String title =
		 * (String) articleRow.get("title");
		 * response.getWriter().append(String.format("<div>%d/%s</div>",id,title)); }
		 *///게시물리스트 id과 타이틀만 가져오도록  articleRows을 하나씩 행을 담아서 출력 새 id와title에 담아서 사용함
			
		response.getWriter().append(articleRows.toString()); //db연결로 가져온 Map타입리스트의 모든행들을 보여준다(문장화해서 가져온다)
		request.setAttribute("articleRows", articleRows);//리스트인 articleRows를 담기위해 setAttribute사용, list.jsp에 보낸다
		request.setAttribute("page", page); //페이지 갯수에 맞춰서 페이지수를 구현하려면 jsp에 page파라미터를 넘겨줘야함
		request.setAttribute("totalPage", totalPage); //전체 페이지 갯수를 jsp에 page파라미터를 넘겨줘야함

		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
		
	}

}
