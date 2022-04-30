package com.myspring.persistence;

import com.myspring.domain.MemberVO;

public interface MemberDAO {
	public MemberVO getMember(String memberId);
	public void insertMember(MemberVO vo);
}
