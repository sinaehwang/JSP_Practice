package com.KoreaIT.java.am.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/article/write")
public class ArticleWriteServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charser=UTF-8");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("MemberLogId")==null) {
			response.getWriter().append(String.format("<script> alert('로그인후 이용바랍니다.');location.replace('../member/login');</script>"));
			return;
		}
		
		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
