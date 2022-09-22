package com.KoreaIT.java.am.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.am.dto.Article;
import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

public class ArticleDao {
	
	private Connection conn;
	
	public ArticleDao(Connection conn) {
		
		this.conn = conn;
		
	}

	public int getTotalCount() {
		
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM article");
		
		int totalCount = DBUtil.selectRowIntValue(conn, sql);
		
		return totalCount;
	}

	public List<Article> getArticleRow(int limitFrom, int itemsInAPage) {
		
		SecSql sql = SecSql.from("SELECT A.*,M.name AS writer");//sql문으로 재수정
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId =M.id");
		sql.append("ORDER BY id DESC");
		sql.append("LIMIT ?,?",limitFrom,itemsInAPage);
		
		List<Map<String,Object>>articleRows = DBUtil.selectRows(conn, sql);
		
		List<Article>articles = new ArrayList<>(); //articleRow를 담기위한 Article객체로 리스트를 만듬
		
		for(Map<String,Object> articleRow :articleRows) {
			
			articles.add(new Article(articleRow)); //리스트 articles에 담기위해 Article객체화를 해줘야함=>dto Article에 맵타입의 생성자를 만든이유!!
		}
			
		return articles;
	}

}
