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

@WebServlet("/article/doModify")
public class ArticleDoModifyServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
			
			int id = Integer.parseInt(request.getParameter("id"));//수정할 게시글번호를 파라미터로 가져온다
			
			String title = request.getParameter("title");
			String body = request.getParameter("body");
			
			SecSql sql =SecSql.from("UPDATE article SET ");
			sql.append("regDate = NOW(),");//게시글 업뎃시 과거 시간이 아닌 수정한 현재시간으로 변경
			sql.append("title = ?,",title);
			sql.append("`body` = ?",body);
			sql.append("WHERE id =?;",id);
		
			DBUtil.update(conn, sql);
			
			response.getWriter().append(String.format("<script> alert('%d번글을 수정했습니다.');location.replace('detail?id=%d');</script>",id,id));//수정후 상세페이지로 넘어가도록함 상세페이지불러오는 양식으로 작성해야 오류가없음!!
		
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
