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

@WebServlet("/article/detail")
public class ArticleDetailServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
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
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			SecSql sql = SecSql.from("SELECT A.*,M.name AS writer");//sql문으로 재수정
			sql.append("FROM article AS A");
			sql.append("INNER JOIN `member` AS M");
			sql.append("ON A.memberId =M.id");
			sql.append("WHERE A.id = ?",id);
			sql.append("ORDER BY id DESC");

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
