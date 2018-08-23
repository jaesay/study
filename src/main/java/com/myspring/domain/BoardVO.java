package com.myspring.domain;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class BoardVO {
	private int bid;
	@Length(max = 20, min = 3)
	private String userid;
	@NotEmpty(message="{NotEmpty.title}")
	@Length(max=200)
	private String title;
	@NotEmpty(message="{NotEmpty.content}")
	private String content;
	private Date regdate;
	private Date upddate;
	private int viewcnt;
	private int commentcnt;
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
