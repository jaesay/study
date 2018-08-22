package com.myspring.service;

import com.myspring.domain.MemberVO;

public interface MemberService {

	public void insertMember(MemberVO vo);

	public MemberVO getMember(String memberId);
}
