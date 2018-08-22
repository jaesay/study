package com.myspring.service;

import javax.inject.Inject;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.myspring.domain.MemberVO;
import com.myspring.persistence.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {
	@Inject
	private MemberDAO dao;
	@Inject
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void insertMember(MemberVO vo) {
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		dao.insertMember(vo);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN') or (#memberId == principal.username)")
	@PostAuthorize("(returnObject == null) or (returnObject.memberId == principal.member.memberId)" )
	public MemberVO getMember(String memberId) {
		return dao.getMember(memberId);
	}
	
}
