package com.jaesay.service;

import com.jaesay.domain.Member;
import com.jaesay.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceCustom {
    private MemberRepository memberRepository;

    public MemberServiceCustom(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long signup (Member member) {
        return memberRepository.save(member).getId();
    }
}
