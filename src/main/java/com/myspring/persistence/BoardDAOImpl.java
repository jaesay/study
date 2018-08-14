package com.myspring.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.myspring.domain.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "com.myspring.mapper.BoardMapper";

	@Override
	public List<BoardVO> getBoardList() throws Exception {
		return session.selectList(namespace + ".getBoardList");
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
	public void deleteBoard(int bid) throws Exception {
		session.delete(namespace + ".deleteBoard", bid);
	}

	@Override
	public void increaseViewcnt(int bid) throws Exception {
		session.update(namespace + ".increaseViewcnt", bid);
	}
}
