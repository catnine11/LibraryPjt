package com.goodee.library.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
	
	private Logger logger = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	BookDao dao;
	
//	public int insertBook(BookVo vo) {
	public int createBookConfirm(BookVo vo) {
		logger.info("[BookService] insertBook();");
//		int result = 0;
//		result = dao.insertBook(vo);
//		return result;
		return dao.insertBook(vo);
	}

}
