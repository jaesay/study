package com.jaesay;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.jaesay.domain.Member;
import com.jaesay.domain.enums.Role;
import com.jaesay.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Commit
@Slf4j
public class MemberTests {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//@Test
	public void save() {
		
		IntStream.range(0, 100).forEach(i -> {
			memberRepository.save(Member.builder()
					.memberName("member" + i)
					.password(bCryptPasswordEncoder.encode("1234"))
					.email("member" + i + "@jaesay.com")
					.role(Role.USER)
					.enabled(true)
					.build());
		});
	}
	
	@Test
	public void findByMemberName() {
			IntStream.range(0, 100).forEach(i -> {
			
				Member member = memberRepository.findByMemberName("member" + (i%10));
				log.info(member.toString());
			});
	}
}
