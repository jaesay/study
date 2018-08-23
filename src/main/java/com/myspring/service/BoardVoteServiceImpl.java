package com.myspring.service;

import javax.inject.Inject;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.domain.BoardVoteVO;
import com.myspring.persistence.BoardDAO;
import com.myspring.persistence.BoardVoteDAO;

@Service
public class BoardVoteServiceImpl implements BoardVoteService {
	@Inject
	private BoardVoteDAO dao;
	@Inject
	private BoardDAO bdao;
	
	@Override
	@Transactional
	@PreAuthorize("hasRole('ADMIN') or (#vo.memberId == principal.username)")
	public void insertBoardVote(BoardVoteVO vo) throws Exception {
		dao.insertBoardVote(vo);
		bdao.updateVotecnt(vo.getBoardId(), 1);
	}

	@Override
	@Transactional
	@PreAuthorize("hasRole('ADMIN') or (#vo.memberId == principal.username)")
	public void deleteBoardVote(BoardVoteVO vo) throws Exception {
		dao.deleteBoardVote(vo);
		bdao.updateVotecnt(vo.getBoardId(), -1);
	}

	@Override
	public int countBoardVote(int bid) throws Exception {
		return dao.countBoardVote(bid);
	}

	@Override
	public int checkBoardVote(BoardVoteVO vo) throws Exception {
		return dao.checkBoardVote(vo);
	}

}
