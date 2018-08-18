package com.myspring.domain;

import java.util.Date;

public class CommentVO {
	int cid;
	int bid;
	String content;
	String userid;
	Date regdate;
	Date upddate;

	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	@Override
	public String toString() {
		return "CommentVO [cid=" + cid + ", bid=" + bid + ", content=" + content + ", userid=" + userid + ", regdate="
				+ regdate + ", upddate=" + upddate + "]";
	}

}
