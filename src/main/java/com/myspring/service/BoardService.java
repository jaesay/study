package com.myspring.service;

import java.util.List;

import com.myspring.domain.BoardVO;
import com.myspring.domain.PageVO;

public interface BoardService {
	public List<BoardVO> getBoardList(PageVO cri) throws Exception;

	public int insertBoard(BoardVO vo) throws Exception;

	public int updateBoard(BoardVO vo) throws Exception;

	public BoardVO getBoard(int bid) throws Exception;

	public void deleteBoard(int bid) throws Exception;
	
	public int countBoard(PageVO pageVO) throws Exception;
}
