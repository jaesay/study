package com.myspring.service;

import java.util.List;

import com.myspring.domain.CommentVO;

public interface CommentService {
	public List<CommentVO> getCommentList(int bid) throws Exception;
	
	public void insertComment(CommentVO vo) throws Exception;
	
	public void updateComment(CommentVO vo) throws Exception;
	
	public void deleteComment(int cid) throws Exception;
	
	public int countComment(int bid) throws Exception;
}
