package com.myspring.service;

import java.util.Collection;

import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myspring.domain.MemberVO;
import com.myspring.domain.UserDetailsVO;
import com.myspring.persistence.MemberDAO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Inject
	MemberDAO dao;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		MemberVO member = dao.getMember(memberId);
        if (member == null) {
            throw new UsernameNotFoundException(memberId);
        }
		return new UserDetailsVO(member, getAuthorities(member));
	}

	private Collection<GrantedAuthority> getAuthorities(MemberVO member) {
		if(member.getRegDate().equals("admin")) {
			return AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		} else {
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}
	}

}
