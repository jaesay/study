package com.myspring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.domain.CommentVO;
import com.myspring.persistence.BoardDAO;
import com.myspring.persistence.CommentDAO;

@Service
public class CommentServiceImpl implements CommentService {

	@Inject
	private CommentDAO dao;
	@Inject
	private BoardDAO bdao;
	
	@Override
	public List<CommentVO> getCommentList(int bid) throws Exception {
		return dao.getCommentList(bid);
	}

	@Transactional
	@Override
	public void insertComment(CommentVO vo) throws Exception {
		dao.insertComment(vo);
		bdao.updateCommentcnt(vo.getBid(), 1);
	}

	@Override
	public void updateComment(CommentVO vo) throws Exception {
		dao.updateComment(vo);
	}

	@Transactional
	@Override
	public void deleteComment(CommentVO vo) throws Exception {
		dao.deleteComment(vo.getCid());
		bdao.updateCommentcnt(vo.getBid(), -1);
	}

	@Override
	public int countComment(int bid) throws Exception {
		return dao.countComment(bid);
	}

}
