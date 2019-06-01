package com.jaesay;

import com.jaesay.domain.Member;
import com.jaesay.service.MemberServiceCustom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModuleApiApplicationTests {

    @Autowired
    private MemberServiceCustom memberServiceCustom;

    @Test
    public void save() {
        Member member = new Member("jaesay", "jaesay@email.com");
        Long id = memberServiceCustom.signup(member);
        assertThat(id, is(1L));
    }
}
