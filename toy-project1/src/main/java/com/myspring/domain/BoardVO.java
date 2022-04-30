package com.myspring.domain;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

public class BoardVO {
	@PositiveOrZero
	private int bid;
	@Length(max = 20, min = 3)
	private String userid;
	@NotEmpty
	@Length(max=20)
	private String title;
	@NotEmpty
	private String content;
	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Date regdate;
	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Date upddate;
	@PositiveOrZero
	private int viewcnt;
	@PositiveOrZero
	private int commentcnt;
	@PositiveOrZero
	private int votecnt;
	
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Date getUpddate() {
		return upddate;
	}
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}
	public int getViewcnt() {
		return viewcnt;
	}
	public void setViewcnt(int viewcnt) {
		this.viewcnt = viewcnt;
	}
	public int getCommentcnt() {
		return commentcnt;
	}
	public void setCommentcnt(int commentcnt) {
		this.commentcnt = commentcnt;
	}
	public int getVotecnt() {
		return votecnt;
	}
	public void setVotecnt(int votecnt) {
		this.votecnt = votecnt;
	}
	@Override
	public String toString() {
		return "BoardVO [bid=" + bid + ", userid=" + userid + ", title=" + title + ", content=" + content + ", regdate="
				+ regdate + ", upddate=" + upddate + ", viewcnt=" + viewcnt + ", commentcnt=" + commentcnt
				+ ", votecnt=" + votecnt + "]";
	}
}
