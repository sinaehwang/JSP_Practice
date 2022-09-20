package com.KoreaIT.java.am.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.KoreaIT.java.am.Config;
import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/member/doLogin")
public class MemberDoLoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charser=UTF-8");

		// DB연결작업(해당서블렛으로 접근했을떄만 db연결을 하게된다
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
			
			String loginId = request.getParameter("loginId");
			String loginPw = request.getParameter("loginPw");
			
			SecSql sql = SecSql.from("SELECT * FROM `member`");
			sql.append("WHERE loginId=?",loginId);
			
			Map<String, Object> Loginmember = DBUtil.selectRow(conn, sql);
			
			if(Loginmember.isEmpty()) {
				response.getWriter().append(String.format("<script> alert('%s는 존재하지않는 회원입니다.');location.replace('login');</script>",loginId));
				return;
			}

			if(((String)Loginmember.get("loginPw")).equals(loginPw)==false) {
				response.getWriter().append(String.format("<script> alert('비밀번호가 일치하지 않습니다.');history.back();</script>"));
				return;
			}
			
		HttpSession session = request.getSession();//request 객체를 이용해 session 정보를 얻어온다.
		session.setAttribute("MemberLogId", Loginmember.get("loginId"));//session에 정보저장할때는 set을 이용한다.


		response.getWriter().append(String.format("<script> alert('%s님 로그인 성공');location.replace('../home/main');</script>",Loginmember.get("name")));
			
			
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
