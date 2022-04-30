package com.myspring.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.myspring.domain.CommentVO;

@Repository
public class CommentDAOImpl implements CommentDAO {
	@Inject
	SqlSession session;
	
	private static String namespace = "com.myspring.mapper.CommentMapper";

	@Override
	public List<CommentVO> getCommentList(int bid) throws Exception {
		return session.selectList(namespace + ".getCommentList", bid);
	}

	@Override
	public void insertComment(CommentVO vo) throws Exception {
		session.insert(namespace + ".insertComment", vo);
	}

	@Override
	public void updateComment(CommentVO vo) throws Exception {
		session.insert(namespace + ".updateComment", vo);
	}

	@Override
	public void deleteComment(int cid) throws Exception {
		session.delete(namespace + ".deleteComment", cid);
	}

	@Override
	public int countComment(int bid) throws Exception {
		return session.selectOne(namespace + ".countComment", bid);
	}

	@Override
	public CommentVO getComment(int cid) throws Exception {
		return session.selectOne(namespace + ".getComment", cid);
	}
	
}
