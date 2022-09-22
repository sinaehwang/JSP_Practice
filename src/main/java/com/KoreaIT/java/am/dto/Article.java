package com.KoreaIT.java.am.dto;

import java.time.LocalDateTime;
import java.util.Map;



public class Article {

	public int id;
	public LocalDateTime regDate;
	public int memberId;
	public String title;
	public String body;
	public String writer;
	
	public Article() {
		
	}
	
	//맵타입으로 가져오는 Article 객체를 만들면 기존에 맵타입의 articlerow 대신 Article 맵타입의 리스트로 저장해 사용가능해짐
	
	public Article(Map<String,Object>row) {
		this.id = (int) row.get("id");
		this.regDate = (LocalDateTime) row.get("regDate");
		this.memberId = (int) row.get("memberId");
		this.title = (String) row.get("title");
		this.body = (String) row.get("body");
		
		if(row.get("writer")!=null) {
			this.writer = (String) row.get("writer");
		}
	}
	
	
	
}
