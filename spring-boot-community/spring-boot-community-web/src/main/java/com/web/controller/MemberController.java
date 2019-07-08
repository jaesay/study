package com.web.controller;

import com.web.domain.Member;
import com.web.repository.MemberRepository;
import com.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final PasswordEncoder passwordEncoder;

    private final MemberService memberService;

    @GetMapping("/join")
    public void join() {}

    @PostMapping("/join")
    public String join(Member member) {
        log.info("member: " + member);
        memberService.join(member);

        return "/member/joinCompleted";
    }


}
