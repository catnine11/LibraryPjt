package com.goodee.library.book;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
	
	private Logger logger = LoggerFactory.getLogger(BookDao.class);
	private final String namespace = "com.goodee.library.book.BookDao.";
	
	@Autowired
//	SqlSession session;
	SqlSession sqlSession;
	
	public int insertBook(BookVo vo) {
		logger.info("[BookDao] insertBook();");
//		int result = 0;
//		result = session.insert(namespace+"insertBook", vo);
//		
//		return result;
		return sqlSession.insert(namespace+"insertBook", vo);
	}
	
//	public List<BookVo> selectBookList(String b_name){
	public List<BookVo> selectBookList(BookVo vo){
		logger.info("[BookDao] selectBookList();");
//		List<BookVo> bookVos = sqlSession.selectList(namespace+"selectBookList", b_name);
		List<BookVo> bookVos = sqlSession.selectList(namespace+"selectBookList", vo);
		return bookVos;
	}
	
	public int selectBookCount(String b_name) {
		logger.info("[BookDao] selectBookCount();");
		int totalCount = sqlSession.selectOne(namespace+"selectBookCount", b_name);
		//selectone이라 개수가 하나 나와야 함(페이지 개수 : 매퍼에 카운트 써주기)
		return totalCount;
	}
	
	public BookVo selectBookOne(int b_no) {
		// BookVo로 받아와서 b_no를 넘겨주면서 전달하겠다.
		logger.info("[BookDao] selectBookOne();");
		BookVo vo = sqlSession.selectOne(namespace+"selectBookOne", b_no);
		
		return vo;
	}

}
