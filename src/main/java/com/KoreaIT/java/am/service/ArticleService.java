package com.KoreaIT.java.am.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.am.util.DBUtil;
import com.KoreaIT.java.am.util.SecSql;

public class ArticleService {

	private Connection conn;
	
	public ArticleService(Connection conn) {
		this.conn = conn;
		
	}

	public int getTotalPage() {
		
		int itemsInAPage =10;
		
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM article");
		
		int totalCount = DBUtil.selectRowIntValue(conn, sql);
		
		int totalPage = (int)Math.ceil((double)totalCount/itemsInAPage);
		
		return totalPage;
		
	}

	public List<Map<String, Object>> getarticleRows(int page) {
		
		int itemsInAPage =10; //15개씩 가져올것

		int limitFrom = (page-1) * itemsInAPage;//page가 1이면 0~14/2이면 15~29까지 가져오게된다.

		
		SecSql sql = SecSql.from("SELECT A.*,M.name AS writer");//sql문으로 재수정
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId =M.id");
		sql.append("ORDER BY id DESC");
		sql.append("LIMIT ?,?",limitFrom,itemsInAPage);
		
		List<Map<String,Object>>articleRows = DBUtil.selectRows(conn, sql);
		
		return articleRows;
	}

}
