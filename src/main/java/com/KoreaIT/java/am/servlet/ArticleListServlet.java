package com.KoreaIT.java.am.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {
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
			
			int page =1;
			
			if(request.getParameter("page") != null && request.getParameter("page").length() !=0 ) {
				try {//try로 감싸면 page=입력받을시 숫자가 아닌 문자로 받게되는 경우를 예외로 처리해주기 위해서//문자로 입력받아도 페이지가 유지됨
					page = Integer.parseInt(request.getParameter("page"));//request로 받은 파라미터 page를 숫자화시킴
				} catch(NumberFormatException e){
					
				}
			}
			
			int itemsInAPage =10; //15개씩 가져올것

			int limitFrom = (page-1) * itemsInAPage;//page가 1이면 0~14/2이면 15~29까지 가져오게된다.
			
			SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
			sql.append("FROM article");
			
			int totalCount = DBUtil.selectRowIntValue(conn, sql);
			int totalPage = (int)Math.ceil((double)totalCount/itemsInAPage);//실수연산을먼저해서 나머지를 발생시킨후 올림을 실행한후에 정수화를 해야함
			
			sql = SecSql.from("SELECT *");//sql문으로 재수정
			sql.append("FROM article");
			sql.append("ORDER BY id DESC");
			sql.append("LIMIT ?,?",limitFrom,itemsInAPage);
				
			List<Map<String,Object>>articleRows = DBUtil.selectRows(conn, sql);//db에 있는 메소드들이 다 static이기 때문에 객체생성할 필요가 없어짐
			

			/*
			 * for(int i=0; i<articleRows.size(); i++) { Map<String,Object>articleRow =
			 * articleRows.get(i); int id = (int) articleRow.get("id"); String title =
			 * (String) articleRow.get("title");
			 * response.getWriter().append(String.format("<div>%d/%s</div>",id,title)); }
			 *///게시물리스트 id과 타이틀만 가져오도록  articleRows을 하나씩 행을 담아서 출력 새 id와title에 담아서 사용함
				
			response.getWriter().append(articleRows.toString()); //db연결로 가져온 Map타입리스트의 모든행들을 보여준다(문장화해서 가져온다)
			request.setAttribute("articleRows", articleRows);//리스트인 articleRows를 담기위해 setAttribute사용, list.jsp에 보낸다
			//setAttribute(객체명,객체)->getAttribute(객체명)으로 받을수있다.
			request.setAttribute("page", page); //페이지 갯수에 맞춰서 페이지수를 구현하려면 jsp에 page파라미터를 넘겨줘야함
			request.setAttribute("totalPage", totalPage); //전체 페이지 갯수를 jsp에 page파라미터를 넘겨줘야함
			request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
			
			
			
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
