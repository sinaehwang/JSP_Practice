package com.KoreaIT.java.am.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.am.dao.ArticleDao;

public class ArticleService {

	private ArticleDao articleDao;
	
	public ArticleService(Connection conn) {
		
		articleDao = new ArticleDao(conn);
		
	}
	
	private int getNumberpage() {// 리스트에서 보여줄 페이지 제한갯수
	
		return 10;
	}
	

	public int getTotalPage() {
		
		int itemsInAPage = getNumberpage();
		
		int totalCount = articleDao.getTotalCount();
		
		int totalPage = (int)Math.ceil((double)totalCount/itemsInAPage);
		
		return totalPage;
		
	}



	public List<Map<String, Object>> getarticleRows(int page) {
		
		int itemsInAPage = getNumberpage();
		
		int limitFrom = (page-1) * itemsInAPage;//page가 1이면 0~14/2이면 15~29까지 가져오게된다.
		
		List<Map<String,Object>>articleRows = articleDao.getArticleRow(limitFrom,itemsInAPage);
		
		return articleRows;
	}

}
