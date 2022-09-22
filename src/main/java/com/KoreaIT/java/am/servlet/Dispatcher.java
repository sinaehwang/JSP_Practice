package com.KoreaIT.java.am.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.KoreaIT.java.am.Config;
import com.KoreaIT.java.am.controller.ArticleController;
import com.KoreaIT.java.am.controller.MemberController;
import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/s/*")
public class Dispatcher extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charser=UTF-8");
		
		//db연결전 올바른 주소요청인지 확인하는 작업후 -> 컨트롤러로 작업이전(각 서블렛에서의 기능을 컨트롤러로 통제)
		
		String requestUri = request.getRequestURI();
		
		String[] requestUriBit = requestUri.split("/");

		if(requestUriBit.length<5) {
			response.getWriter().append(String.format("<script> alert('올바른 요청이 아닙니다.');</script>"));
			return;
		}

		String url = Config.getUrl();

		String user = Config.getUser();

		String password = Config.getPassWord();

		Connection conn = null;
		
		String driverName = Config.getDriverName();
		
		try {
			Class.forName(driverName);
			
			

		} catch (ClassNotFoundException e) {
			System.out.println("예외 : 클래스가 없습니다.");
			System.out.println("프로그램을 종료합니다.");
			
		}

		try {
			conn = DriverManager.getConnection(Config.getUrl(), Config.getUser(), Config.getPassWord());
			
			//db연결후 모든작업전에 해야하는일 
			HttpSession session = request.getSession();
			
			boolean isLogined = false;
			int loginedMemberId =-1;
			Map<String,Object>loginedMemberRow =null; //로그인이 됬다면 sql을 이용해 로그인된 멤버에 전체 정보를 가져와서 덮어씌워서 사용가능
			
			if(session.getAttribute("MemberLogId")!=null) {
				loginedMemberId=(int)session.getAttribute("loginedMemberId");
				isLogined = true;
				
				SecSql sql = SecSql.from("SELECT * FROM `member`");
				sql.append("WHERE id = ?",loginedMemberId);
				
				loginedMemberRow = DBUtil.selectRow(conn, sql);
				
			}
			
			request.setAttribute("loginedMemberId", loginedMemberId);
			request.setAttribute("isLogined", isLogined);
			request.setAttribute("loginedMemberRow", loginedMemberRow);
			
			//db연결후 모든작업전에 해야하는일 
			
			
			String controllerName = requestUriBit[3];
			
			String actionMethodName = requestUriBit[4];
			
			if(controllerName.equals("article")) {
				ArticleController articleController = new ArticleController(conn,request,response);
				
				if(actionMethodName.equals("list")) {
					
					articleController.actionList();
				}
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
