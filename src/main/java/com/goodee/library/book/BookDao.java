package com.goodee.library.book;

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

}
