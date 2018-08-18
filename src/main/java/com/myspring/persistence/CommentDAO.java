package com.myspring.persistence;

import java.util.List;

import com.myspring.domain.CommentVO;

public interface CommentDAO {
	public List<CommentVO> getCommentList(int bid) throws Exception;
	
	public void insertComment(CommentVO vo) throws Exception;
	
	public void updateComment(CommentVO vo) throws Exception;
	
	public void deleteComment(int cid) throws Exception;
	
	public int countComment(int bid) throws Exception;
}
