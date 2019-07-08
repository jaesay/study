package com.web.repository;

import com.web.domain.Member;
import com.web.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void init() {
        IntStream.rangeClosed(0, 100).forEach(i -> {
            Member member = Member.builder()
                    .memberId("user" + i)
                    .password("pw" + i)
                    .email("user"+ i + "@email.com")
                    .build();

            Role role = new Role();
            if(i <= 80) {
                role.setRoleName("BASIC");
            } else if(i <= 90) {
                role.setRoleName("MANAGER");
            } else {
                role.setRoleName("ADMIN");
            }
            member.setRoles(List.of(role));

            memberRepository.save(member);
        });
    }

    @Test
    public void test() {
        Optional<Member> member = memberRepository.findById(85L);

        member.ifPresent(m -> log.info("member: " + m.getRoles()));
    }
}