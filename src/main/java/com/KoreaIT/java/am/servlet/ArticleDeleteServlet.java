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

@WebServlet("/article/doDelete")
public class ArticleDeleteServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charser=UTF-8");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("MemberLogId")==null) {
			response.getWriter().append(String.format("<script> alert('로그인후 이용바랍니다.');location.replace('../member/login');</script>"));
			return;
		}
		
		// DB연결작업(해당서블렛으로 접근했을떄만 db연결을 하게된다
		String url = Config.getUrl();

		String user = Config.getUser();

		String password = Config.getPassWord();

		Connection conn = null;
		
		//driver 연결
		String driverName = Config.getDriverName();
		
		try {
			Class.forName(driverName);

		} catch (ClassNotFoundException e) {
			System.out.println("예외 : 클래스가 없습니다.");
			System.out.println("프로그램을 종료합니다.");
			
		}

		try {
			conn = DriverManager.getConnection(Config.getUrl(), Config.getUser(), Config.getPassWord());
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			SecSql sql = SecSql.from("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			
			Map<String,Object>articleRow = DBUtil.selectRow(conn, sql);
			
			int loginedMemberId =(int)session.getAttribute("loginedMemberId");
			
			if(loginedMemberId != (int)articleRow.get("memberId")) {
				
				response.getWriter().append(String.format("<script> alert('해당글에 대한 권한이 없습니다.');location.replace('../article/list');</script>"));
				return;
			}
			
			sql = SecSql.from("DELETE FROM article");//sql문으로 재수정
			sql.append("WHERE id=?",id);

			DBUtil.delete(conn, sql);//jsp와 협업필요없이 바로 삭제하면 되니까
			
			response.getWriter().append(String.format("<script> alert('%d번글을 삭제했습니다.');location.replace('list');</script>",id));
			
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
