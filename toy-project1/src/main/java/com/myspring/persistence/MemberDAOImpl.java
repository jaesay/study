package com.myspring.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.myspring.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	@Inject
	SqlSession session;
	
	private static String namespace = "com.myspring.mapper.MemberMapper";
	
	@Override
	public MemberVO getMember(String memberId) {
		return session.selectOne(namespace + ".getMember", memberId);
	}

	@Override
	public void insertMember(MemberVO vo) {
		session.insert(namespace + ".insertMember", vo);
	}
	
	
}
