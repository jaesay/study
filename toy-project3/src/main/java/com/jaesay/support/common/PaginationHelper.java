package com.jaesay.support.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString(exclude="pageList")
public class PaginationHelper<T> {

	private Page<T> result;
	
	private Pageable prevPage;
	private Pageable nextPage;
	
	private int currentPageNum;
	private int totalPageNum;
	
	private Pageable currentPage;
	
	private List<Pageable> pageList;

	public PaginationHelper(Page<T> result) {
		this.result = result;
		this.currentPage = result.getPageable();
		this.currentPageNum = currentPage.getPageNumber() + 1;
		this.totalPageNum = result.getTotalPages();
		this.pageList = new ArrayList<>();
		calcPages();
	}

	private void calcPages() {
		int tempEndNum = (int)(Math.ceil(this.currentPageNum/5.0) * 5);
		int startNum = tempEndNum - 4;
		Pageable startPage = this.currentPage;
		
		for(int i = startNum; i<this.currentPageNum; i++) {
			startPage = startPage.previousOrFirst();
		}
		this.prevPage = startPage.getPageNumber() <= 0 ? null : startPage.previousOrFirst();
				
		if(this.totalPageNum < tempEndNum) {
			tempEndNum = this.totalPageNum;
			this.nextPage = null;
		}
		
		for(int i = startNum; i<=tempEndNum; i++) {
			pageList.add(startPage);
			startPage = startPage.next();
		}
		this.nextPage = startPage.getPageNumber() < totalPageNum ? startPage : null;
	}
	
}
