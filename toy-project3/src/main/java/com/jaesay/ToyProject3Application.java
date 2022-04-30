package com.jaesay;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jaesay.domain.Member;
import com.jaesay.domain.enums.Role;
import com.jaesay.repository.MemberRepository;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class ToyProject3Application {
	
	public static void main(String[] args) {
		SpringApplication.run(ToyProject3Application.class, args);
	}
	
	@Bean
	public CommandLineRunner runner(MemberRepository memberRepository) {
		return (args) -> {
			Optional<Member> member = Optional.ofNullable(memberRepository.findByMemberName("admin"));
			if(!member.isPresent()) {
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				memberRepository.save(Member.builder()
						.memberName("admin")
						.password(bCryptPasswordEncoder.encode("1234"))
						.email("admin@admin.com")
						.enabled(true)
						.role(Role.ADMIN)
						.build());							
				
			}
		};
	}
}

