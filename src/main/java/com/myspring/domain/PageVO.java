package com.myspring.domain;

import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;

public class PageVO {

	@Positive
	private int page;
	@Range(min=1, max=50)
	private int perPageNum;
	
	public PageVO() {
		this.page = 1;
		this.perPageNum = 10;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if(page<=0) {
			this.page = 1;
			return;
		}
		
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		if(perPageNum <= 0 || perPageNum > 50) {
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}
	
	public int getPageStart() {
		return (this.page -1) * perPageNum;
	}

	@Override
	public String toString() {
		return "PaginationVO [page=" + page + ", perPageNum=" + perPageNum + "]";
	}
	
}
