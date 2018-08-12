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
	public List<BoardVO> getAll() throws Exception {
		return session.selectList(namespace + ".getAll");
	}
}
