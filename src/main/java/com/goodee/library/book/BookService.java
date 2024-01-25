package com.goodee.library.book;

import java.util.List;

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
	
//	public List<BookVo> selectBookList(String b_name){
	public List<BookVo> selectBookList(BookVo vo){
		// 목록 전달 정보니까Vo로(vo하나로 받아오는걸 vo가 여러개니까 List<Vo> 형태로)
		// result : 어떤 정보를 전달할지, parameter : 어떤 정보를 전달받아올지
		logger.info("[BookService] selectBookList();");
//		List<BookVo> bookVos = dao.selectBookList(b_name);
		List<BookVo> bookVos = dao.selectBookList(vo);
		
		return bookVos;
	}
	
	public int selectBookCount(String b_name) {
		logger.info("[BookService] selectBookCount();");
		int totalCount = dao.selectBookCount(b_name);
		return totalCount;
	}
	
	public BookVo bookDetail(int b_no) {
		logger.info("[BookService] bookDetail();");
		BookVo vo = dao.selectBookOne(b_no);
		return vo;
	}
	
	public int modifyConfirm(BookVo vo) {
		logger.info("[BookService] modifyConfirm();");
		int result = -1;
		result = dao.updatBook(vo);
		
		return result;
	}
	
	public int deleteBook(int b_no) {
		logger.info("[BookService] deleteBook();");
		return dao.deleteBook(b_no);
	}
	

}
