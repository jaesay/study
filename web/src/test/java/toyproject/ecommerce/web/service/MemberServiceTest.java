package toyproject.ecommerce.web.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.Member;
import toyproject.ecommerce.core.repository.MemberRepository;
import toyproject.ecommerce.core.domain.enums.Role;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void signUpSuccess() throws Exception {
        //given
        Member member = Member.builder()
                .email("user@email.com")
                .password("1234")
                .name("user")
                .role(Role.USER)
                .build();

        //when
        Long saveId = memberService.signUp(member);

        //then
        assertThat(member).isEqualTo(memberRepository.findById(saveId).get());
    }

    @Test(expected = IllegalStateException.class)
    public void validateDuplicateMember() throws Exception {
        //given
        Member member1 = Member.builder()
                .email("user1@email.com")
                .password("1234")
                .name("user1")
                .role(Role.USER)
                .build();

        Member member2 = Member.builder()
                .email("user1@email.com")
                .password("1234")
                .name("user2")
                .role(Role.USER)
                .build();

        //when
        memberService.signUp(member1);
        memberService.signUp(member2); //Cause Exception

        //then
        fail("예외가 발생해야 한다.");
    }
}