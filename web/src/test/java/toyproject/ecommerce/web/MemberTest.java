package toyproject.ecommerce.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.Member;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberTest {

    @Test
    public void 테스트() {
        Member member = new Member();
    }
}
