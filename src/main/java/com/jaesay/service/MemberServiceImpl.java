package com.jaesay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jaesay.domain.Member;
import com.jaesay.repository.MemberRepository;


@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(Member member) {
		member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
		memberRepository.save(member);
	}

	@Override
	public Member findByMemberName(String memberName) {
		return memberRepository.findByMemberName(memberName);
	}

	@Override
	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}
	
}
