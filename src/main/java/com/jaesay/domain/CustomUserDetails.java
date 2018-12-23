package com.jaesay.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jaesay.domain.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails extends User {

	private static final long serialVersionUID = -2633689221956637605L;

	private static final String ROLE_PREFIX = "ROLE_";
	
	private Member member;
	
	public CustomUserDetails(Member member) {
		super(member.getMemberName(), member.getPassword(), getAuthorities(member.getRole()));
		this.member = member;
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getValue()));
		return list;
	}

}
