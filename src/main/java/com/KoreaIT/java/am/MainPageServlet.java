package com.KoreaIT.java.am;

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
import jakarta.servlet.http.HttpSession;

@WebServlet("/home/main")
public class MainPageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//DB연결(home/main에서 바로 DB연결, 세션으로 현재 로그인 상태유무 불러온다)
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
			
			request.getRequestDispatcher("/jsp/home/main.jsp").forward(request, response); //main.jsp에서 로그인상태인 회원기본정보 모두 사용가능해짐
			

			
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

}
