package com.myspring.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.myspring.domain.BoardVO;
import com.myspring.domain.PageVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "com.myspring.mapper.BoardMapper";

	@Override
	public List<BoardVO> getBoardList(PageVO pageVO) throws Exception {
		return session.selectList(namespace + ".getBoardList", pageVO);
	}

	@Override
	public int insertBoard(BoardVO vo) throws Exception {
		session.insert(namespace + ".insertBoard", vo);
		return vo.getBid();
	}

	@Override
	public int updateBoard(BoardVO vo) throws Exception {
		session.update(namespace + ".updateBoard", vo);
		return vo.getBid();
	}

	@Override
	public BoardVO getBoard(int bid) throws Exception {
		return session.selectOne(namespace + ".getBoard", bid);
	}

	@Override
	public void deleteBoard(BoardVO vo) throws Exception {
		session.delete(namespace + ".deleteBoard", vo);
	}

	@Override
	public void increaseViewcnt(int bid) throws Exception {
		session.update(namespace + ".increaseViewcnt", bid);
	}

	@Override
	public int countBoard(PageVO pageVO) throws Exception {
		return session.selectOne(namespace + ".countBoard", pageVO);
	}

	@Override
	public void updateCommentcnt(int bid, int amount) throws Exception {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		
		paramMap.put("bid", bid);
		paramMap.put("amount", amount);
		
		session.update(namespace + ".updateCommentcnt", paramMap);
	}

	@Override
	public void updateVotecnt(int bid, int amount) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		
		paramMap.put("bid", bid);
		paramMap.put("amount", amount);
		
		session.update(namespace + ".updateVotecnt", paramMap);
	}
}
