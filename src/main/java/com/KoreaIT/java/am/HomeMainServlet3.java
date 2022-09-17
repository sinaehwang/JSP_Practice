package com.KoreaIT.java.am;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/home/printDan4")
public class HomeMainServlet3 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html;charser=UTF-8");//Post 방식을 인코딩 html로 변환작업함
		
		String inputedDan = request.getParameter("dan"); //웹브라우저 dan의 값을 파라미터로 받아서 inputedDan에저장
		
		String inputedLimit = request.getParameter("limit");//limit값을 받아서 inputedLimit에 저장
		
		String inputedColor = request.getParameter("color");
		
		String inputedBgColor = request.getParameter("bgcolor");
		
		if(inputedDan==null) {
			inputedDan = "1";
		}
		
		if(inputedLimit==null) {
			inputedLimit="9";
		}
		
		if(inputedColor==null) {
			inputedColor="black";//black생략가능 color의css기본값은 정해져있기때문에
		}
		
		if(inputedBgColor==null) {
			inputedColor="white";
		}
		
		int dan=Integer.parseInt(inputedDan); //파라미터로 받은 input은 문자이기 때문에 숫자로 변환해서 dan에 저장

		int limit = Integer.parseInt(inputedLimit);

		
		//response.getWriter().append(String.format("<div>%d단</div>",dan));
		//response.getWriter().append(String.format("<div style=\"color:red;\">%d단</div>",dan));//쌍따옴표까지 출력하려면 쌍따옴표앞에 역슬래쉬 붙이고 그안에 내용을 써주면됨
		response.getWriter().append(String.format("<div style=\"color:%s; background-color:%s;\">%d단</div><br />",inputedColor,inputedBgColor,dan));//컬러도 인자값으로 받아서 처리함
		for(int i=1; i<=limit; i++) {//limit지점까지 구구단을 출력할수 있다.
			
			response.getWriter().append(String.format("<div style=\"color:%s; background-color:%s;\">%d*%d=%d<br></div><br />",inputedColor,inputedBgColor,dan,i,dan*i));
			
		}
	}

}
