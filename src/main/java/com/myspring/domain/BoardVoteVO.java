package com.myspring.domain;

import java.util.Date;

public class BoardVoteVO {
	int bvid;
	String memberId;
	int boardId;
	Date regDate;
	public int getBvid() {
		return bvid;
	}
	public void setBvid(int bvid) {
		this.bvid = bvid;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "BoardVoteVO [bvid=" + bvid + ", memberId=" + memberId + ", boardId=" + boardId + ", regDate=" + regDate
				+ "]";
	}
}
