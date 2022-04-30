package com.myspring.service;

import com.myspring.domain.BoardVoteVO;

public interface BoardVoteService {

	public void insertBoardVote(BoardVoteVO vo) throws Exception;

	public void deleteBoardVote(BoardVoteVO vo) throws Exception;

	public int countBoardVote(int bid) throws Exception;

	public int checkBoardVote(BoardVoteVO vo) throws Exception;
	
}
