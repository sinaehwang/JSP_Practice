package com.KoreaIT.java.am;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/home/printDan3")
public class HomePrintDanServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html;charser=UTF-8");
		request.getRequestDispatcher("/jsp/home/printDan.jsp").forward(request, response);
		//서블릿이 jsp에게 위임해서 처리함 jsp실행파일생성후 실행동작작성 => 서블릿 실행할 java클래스에서 ctrl+F11 
	}

}
