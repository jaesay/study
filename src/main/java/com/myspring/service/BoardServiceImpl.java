package com.myspring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.domain.BoardVO;
import com.myspring.domain.PageVO;
import com.myspring.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO dao;
	
	@Override
	public List<BoardVO> getBoardList(PageVO pageVO) throws Exception {
		return dao.getBoardList(pageVO);
	}

	@Override
	public int insertBoard(BoardVO vo) throws Exception {
		return dao.insertBoard(vo);
	}

	@Override
	public int updateBoard(BoardVO vo) throws Exception {
		return dao.updateBoard(vo);
	}

	@Transactional
	@Override
	public BoardVO getBoard(int bid) throws Exception {
		dao.increaseViewcnt(bid);
		return dao.getBoard(bid);
	}

	@Override
	public void deleteBoard(int bid) throws Exception {
		dao.deleteBoard(bid);
	}

	@Override
	public int countBoard(PageVO pageVO) throws Exception {
		return dao.countBoard(pageVO);
	}

}
