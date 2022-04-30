package com.jaesay;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.jaesay.domain.Board;
import com.jaesay.domain.Member;
import com.jaesay.repository.BoardRepository;
import com.jaesay.repository.MemberRepository;
import com.jaesay.service.BoardService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Commit
public class BoardTests {
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	public void saveBoard() {
		
		IntStream.range(0, 100).forEach(i -> {
			
			Member member = memberRepository.findByMemberName("member" + (i%10));
			boardRepository.save(Board.builder()
					.member(member)
					.title("title" + i)
					.content("content...................." + i)
					.build());
		});
		
	}

}
