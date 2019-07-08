package com.web.service;

import com.web.domain.Member;
import com.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public void join(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberRepository.save(member);
    }
}
