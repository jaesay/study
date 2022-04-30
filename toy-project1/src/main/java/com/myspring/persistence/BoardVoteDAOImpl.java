package com.myspring.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.myspring.domain.BoardVoteVO;

@Repository
public class BoardVoteDAOImpl implements BoardVoteDAO {
	@Inject
	private SqlSession session;
	
	private static String namespace = "com.myspring.mapper.BoardVoteMapper";
	
	@Override
	public void insertBoardVote(BoardVoteVO vo) throws Exception {
		session.insert(namespace + ".insertBoardVote", vo);
	}

	@Override
	public void deleteBoardVote(BoardVoteVO vo) throws Exception {
		session.delete(namespace + ".deleteBoardVote", vo);
	}

	@Override
	public int countBoardVote(int bid) throws Exception {
		return session.selectOne(namespace + ".countBoardVote", bid);
	}

	@Override
	public int checkBoardVote(BoardVoteVO vo) throws Exception {
		return session.selectOne(namespace + ".checkBoardVote", vo);
	}

}
