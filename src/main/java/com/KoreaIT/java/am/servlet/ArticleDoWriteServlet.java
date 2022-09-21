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
import jakarta.servlet.http.HttpSession;

@WebServlet("/article/doWrite")
public class ArticleDoWriteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");//게시글 작성시 한글깨짐 방지
		response.setContentType("text/html;charser=UTF-8");

		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginedMemberId")==null) {
			response.getWriter().append(String.format("<script> alert('로그인후 이용해주세요');location.replace('../member/login');</script>"));
		}
		
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
			
			String title = request.getParameter("title");
			String body = request.getParameter("body");
			
			int loginedMemberId = (int)session.getAttribute("loginedMemberId");
			
			SecSql sql= SecSql.from("INSERT INTO article SET");
			sql.append("regDate=NOW(),");
			sql.append("memberId=?,",loginedMemberId);
			sql.append("title=?,",title);
			sql.append("`body`=?",body);
			
			int id = DBUtil.insert(conn, sql);
			response.getWriter().append(String.format("<script> alert('%d번글이 생성');location.replace('list');</script>",id));
			
			
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
