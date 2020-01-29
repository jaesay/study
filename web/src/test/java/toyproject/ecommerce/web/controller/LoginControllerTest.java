package toyproject.ecommerce.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import toyproject.ecommerce.core.domain.member.Member;
import toyproject.ecommerce.core.domain.member.MemberRepository;
import toyproject.ecommerce.core.domain.member.Role;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class LoginControllerTest {

    @Autowired private WebApplicationContext context;

    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired private MemberRepository memberRepository;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Member member = Member.builder()
                .email("user@test.com")
                .passwod(bCryptPasswordEncoder.encode("1234"))
                .name("name1")
                .role(Role.USER)
                .build();

        memberRepository.save(member);
    }

    @Test
    public void testFormLogin() throws Exception {
        mvc.perform(formLogin().user("user@test.com").password("1234"))
                .andExpect(status().isFound()).andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withRoles("USER"));
    }

    @WithMockUser
    @Test
    public void testLogout() throws Exception {
        mvc.perform(logout())
                .andExpect(status().isFound()).andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }
}