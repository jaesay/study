package com.web;

import com.web.domain.Board;
import com.web.domain.Member;
import com.web.domain.Role;
import com.web.domain.enums.BoardType;
import com.web.repository.BoardRepository;
import com.web.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class SpringBootCommunityWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCommunityWebApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(BoardRepository boardRepository, MemberRepository memberRepository, PasswordEncoder passwordEncoder) throws Exception {
        return (args) -> {
            Member member = memberRepository.save(Member.builder()
                    .memberId("jaesay")
                    .password(passwordEncoder.encode("1111"))
                    .email("jaesay@email.com")
                    .roles(List.of(Role.builder().roleName("ADMIN").build()))
                    .build());

            IntStream.rangeClosed(1, 200).forEach(index ->
                    boardRepository.save(Board.builder()
                        .title("게시글" + index)
                        .subTitle("순서" + index)
                        .content("콘텐츠")
                        .boardType(BoardType.free)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .member(member)
                        .build()
                    )
            );


        };
    }

}
