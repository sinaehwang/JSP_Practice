package com.KoreaIT.java.am.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.KoreaIT.java.am.Config;
import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/doJoin")
public class MemberDoJoinServlet extends HttpServlet {

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
			String name = request.getParameter("name");
			
			SecSql sql = SecSql.from("SELECT COUNT(*) FROM `member`");
			sql.append("WHERE loginId=?",loginId);
			
			int isJoinavailableLoginId = DBUtil.selectRowIntValue(conn, sql);

			if(isJoinavailableLoginId>0) {//같은 아이디가 있다면 0보다 클꺼니까
				response.getWriter().append(String.format("<script> alert('%s는 이미 가입된 아이디입니다.');location.replace('join');</script>",loginId));
				return;
			}
			
			sql= SecSql.from("INSERT INTO `member` SET");
			sql.append("regDate=NOW(),");
			sql.append("loginId=?,",loginId);
			sql.append("loginPw=?,",loginPw);
			sql.append("`name`=?",name);
			
			int id = DBUtil.insert(conn, sql);
			response.getWriter().append(String.format("<script> alert('%s님 가입완료');location.replace('../home/main');</script>",name));
			
			
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
