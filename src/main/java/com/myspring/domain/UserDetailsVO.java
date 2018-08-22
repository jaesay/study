package com.myspring.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetailsVO extends User {

	private static final long serialVersionUID = 1189025522587027848L;

	private final MemberVO member;

	public UserDetailsVO(MemberVO member, Collection<? extends GrantedAuthority> authorities) {
		super(member.getMemberId(), member.getPassword(), member.isEnabled(), true, true, true, authorities);
		
		this.member = member;
	}

	public MemberVO getMember() {
		return member;
	}

}
