package com.KoreaIT.java.am;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/home/main")
public class MainPageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String loginId=null;
		
		if(session.getAttribute("MemberLogId")!=null) {
			loginId=(String) request.getAttribute("MemberLogId");
		}
		
		request.setAttribute("MemberLogId", loginId);
		
		request.getRequestDispatcher("/jsp/home/main.jsp").forward(request, response);
		
	}

}
