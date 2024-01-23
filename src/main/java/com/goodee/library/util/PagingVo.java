package com.goodee.library.util;

public class PagingVo {
	
	//이 vo를 여러 곳에서 쓰고싶을 때 util에 모아서 상속받을 수 있도록,,
	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private int pageSize = 10;
	private int page_no =1;
	private int limit_pageNo;
	
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		//페이징 계산
		calcData();
	}
	
	private void calcData() {
		// 전체 개수 33, 현재 누른 페이지 3 가정
		limit_pageNo = (page_no-1) * pageSize;  //한 화면에 정보 몇개 10개
			//최대 페이지 : 페이지개수-1*10만큼 있음 () , 초기값 0임
//		endPage = (int) (Math.ceil(totalCount /(double)pageSize));  //ceil : 올림
		endPage = (int)(Math.ceil(page_no/(double)pageSize)*pageSize); //페이지 바에 페이지 10개 //초기값 1
		startPage = (endPage - pageSize) +1; //페이지바에 페이지 10개
		
		int tempEndPage = (int)(Math.ceil(totalCount/(double)pageSize)); //한 화면에 정보 몇개 10
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		prev = startPage == 1? false:true; // 시작페이지가 1이면 이전페이지 없고, 그 다음이면 있음
		next = endPage * pageSize >= totalCount ? false : true; //한 화면에 정보 몇개? 10
		//마지막페이지*페이지 개수 가 총개수보다 많으면 안 뜨게
	}
	
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage_no() {
		return page_no;
	}
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}
	public int getLimit_pageNo() {
		return limit_pageNo;
	}
	public void setLimit_pageNo(int limit_pageNo) {
		this.limit_pageNo = limit_pageNo;
	}
	
	

}
