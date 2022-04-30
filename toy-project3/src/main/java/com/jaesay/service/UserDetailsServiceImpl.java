package com.jaesay.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jaesay.domain.CustomUserDetails;
import com.jaesay.domain.Member;
import com.jaesay.repository.MemberRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	MemberRepository memberRepository;
	
	final static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = Optional.ofNullable(memberRepository.findByMemberName(username))
							.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
		
		return new CustomUserDetails(member);
	}

}