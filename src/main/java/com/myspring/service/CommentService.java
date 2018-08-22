package com.myspring.service;

import java.util.List;

import com.myspring.domain.CommentVO;

public interface CommentService {
	public List<CommentVO> getCommentList(int bid) throws Exception;
	
	public void insertComment(CommentVO vo) throws Exception;
	
	public void updateComment(CommentVO vo) throws Exception;
	
	public void deleteComment(CommentVO vo) throws Exception;
	
	public int countComment(int bid) throws Exception;

	public CommentVO getComment(int cid) throws Exception;
}
