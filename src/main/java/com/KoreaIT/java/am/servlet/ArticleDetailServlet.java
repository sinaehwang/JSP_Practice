package com.KoreaIT.java.am.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/article/detail")
public class ArticleDetailServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
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
			
			//response.getWriter().append("success");//연결테스트해서 출력나타내보기
			
			//DBUtil dbutil = new DBUtil(request,response); //연결이 완료되면 DB를 바로 사용함 서버에 대한 응답을 하려면 db 연결시 생성자를 넘겨줘야함
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			SecSql sql =SecSql.from("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id=?",id);

			
			Map<String,Object>articleRow = DBUtil.selectRow(conn, sql);//입력된id 글하나만 가져올것이기 때문에 selectRow로 바꿔줌
			
			request.setAttribute("articleRow", articleRow);
			request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);//detail url로 가져온다
			
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
