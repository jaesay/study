package toyproject.ecommerce.web.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.Member;
import toyproject.ecommerce.core.domain.enums.Role;
import toyproject.ecommerce.core.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CartServiceTest {

    @Autowired CartService cartService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void saveCart() throws Exception {
        //given
        Member member = Member.builder()
                .email("user@email.com")
                .password("1234")
                .name("user")
                .role(Role.USER)
                .build();
        memberRepository.save(member);

        //when
        Long cartId = cartService.save(member);

        //then
        assertThat(cartId).isGreaterThan(0L);
    }
}