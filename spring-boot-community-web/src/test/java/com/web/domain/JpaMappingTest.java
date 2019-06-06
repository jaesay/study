package com.web.domain;

import com.web.domain.enums.BoardType;
import com.web.repository.BoardRepository;
import com.web.repository.MemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMappingTest {

    private final String boardTestTitle = "테스트";
    private final String email = "test@email.com";

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Before
    public void init() {
        Member member = memberRepository.save(Member.builder()
                .memberId("jaesay1")
                .password(passwordEncoder.encode("1111"))
                .email("jaesay1@email.com")
                .roles(List.of(Role.builder().roleName("MANAGER").build()))
                .build());

        boardRepository.save(Board.builder()
                .title(boardTestTitle)
                .subTitle("서브 타이틀")
                .content("콘텐츠")
                .boardType(BoardType.free)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .member(member)
                .build());
    }

    @Test
    public void test() {
        Member member = memberRepository.findByMemberId("jaesay1").orElse(new Member());
        assertThat(member.getEmail()).isEqualTo("jaesay1@email.com");

        Board board = boardRepository.findByMember(member);
        assertThat(board.getTitle()).isEqualTo(boardTestTitle);
    }
}