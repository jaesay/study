package com.jaesay.member;

import com.jaesay.domain.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/")
    public Member get() {
        return new Member("jaesay", "jaesay@email.com");
    }
}
