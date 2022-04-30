package com.jaesay.service;

import com.jaesay.domain.Member;

public interface MemberService {
	void save(Member member);
	Member findByMemberName(String memberName);
	Member findByEmail(String email);
}
