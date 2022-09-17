package com.KoreaIT.java.am.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

@WebServlet("/article/modify")
public class ArticleModifyServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charser=UTF-8");

		// DB연결작업(해당서블렛으로 접근했을떄만 db연결을 하게된다
		String url = "jdbc:mysql://127.0.0.1:3306/JSPTest?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

		String user = "root";

		String password = "";

		Connection conn = null;
		
		String driverName = "com.mysql.jdbc.Driver";
		
		try {
			Class.forName(driverName);

		} catch (ClassNotFoundException e) {
			System.out.println("예외 : 클래스가 없습니다.");
			System.out.println("프로그램을 종료합니다.");
			
		}

		try {
			conn = DriverManager.getConnection(url, user, password);
			
			int id = Integer.parseInt(request.getParameter("id"));//수정할 게시글번호를 파라미터로 가져온다
			
			SecSql sql =SecSql.from("SELECT * FROM article ");
			sql.append("WHERE id =?",id);
			
			Map<String,Object>articleRow = DBUtil.selectRow(conn, sql);
			
			request.setAttribute("articleRow", articleRow);
			request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
		
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
