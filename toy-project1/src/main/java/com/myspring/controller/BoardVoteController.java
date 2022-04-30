package com.myspring.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myspring.domain.BoardVoteVO;
import com.myspring.domain.UserDetailsVO;
import com.myspring.service.BoardVoteService;

@RestController
@RequestMapping("/board_votes")
public class BoardVoteController {
	@Inject
	private BoardVoteService service;
	
	@GetMapping("/{bid}")
	public ResponseEntity<String> checkVote(@PathVariable("bid") int bid, @AuthenticationPrincipal UserDetailsVO userDetails) {
		ResponseEntity<String> entity = null;
		
		try {
			BoardVoteVO vo = new BoardVoteVO();
			vo.setBoardId(bid);
			vo.setMemberId(userDetails.getMember().getMemberId());
			int result = service.checkBoardVote(vo);				
			entity = new ResponseEntity<String>(result+"", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@PostMapping("")
	public ResponseEntity<String> voteGood(@RequestBody BoardVoteVO vo, @AuthenticationPrincipal UserDetailsVO userDetails) {
		ResponseEntity<String> entity = null;
		
		try {
			vo.setMemberId(userDetails.getMember().getMemberId());
			service.insertBoardVote(vo);				
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@DeleteMapping(value="")
	public ResponseEntity<String> deleteComment(@RequestBody BoardVoteVO vo, @AuthenticationPrincipal UserDetailsVO userDetails) {
		
		ResponseEntity<String> entity = null;
		
		try {
			vo.setMemberId(userDetails.getMember().getMemberId());
			service.deleteBoardVote(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@GetMapping("/cnt/{bid}")
	public ResponseEntity<String> countComment(@PathVariable("bid") int bid) {
		ResponseEntity<String> entity = null;
		
		try {
			int commentNum = service.countBoardVote(bid);
			entity = new ResponseEntity<String>(commentNum+"", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}
