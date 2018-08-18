package com.myspring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.myspring.domain.CommentVO;
import com.myspring.persistence.CommentDAO;

@Service
public class CommentServiceImpl implements CommentService {

	@Inject
	private CommentDAO dao;
	
	@Override
	public List<CommentVO> getCommentList(int bid) throws Exception {
		return dao.getCommentList(bid);
	}

	@Override
	public void insertComment(CommentVO vo) throws Exception {
		dao.insertComment(vo);
	}

	@Override
	public void updateComment(CommentVO vo) throws Exception {
		dao.updateComment(vo);
	}

	@Override
	public void deleteComment(int cid) throws Exception {
		dao.deleteComment(cid);
	}

	@Override
	public int countComment(int bid) throws Exception {
		return dao.countComment(bid);
	}

}
