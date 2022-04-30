package com.myspring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myspring.domain.CommentVO;
import com.myspring.domain.UserDetailsVO;
import com.myspring.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Inject
	private CommentService service;
	
	@PostMapping("")
	public ResponseEntity<String> insertComment(@RequestBody CommentVO vo, @AuthenticationPrincipal UserDetailsVO userDetails) {
		
		ResponseEntity<String> entity = null;
		
		try {
			vo.setUserid(userDetails.getMember().getMemberId());
			service.insertComment(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@GetMapping("/all/{bid}")
	public ResponseEntity<Map<String, Object>> getCommentList(@PathVariable("bid") int bid, @AuthenticationPrincipal UserDetailsVO userDetails) {
		ResponseEntity<Map<String, Object>> entity = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<CommentVO> commentList = service.getCommentList(bid);
			map.put("commentList", commentList);
			
			if(userDetails != null) {
				map.put("memberId", userDetails.getMember().getMemberId());
			}
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@PutMapping(value="/{cid}")
	public ResponseEntity<String> updateComment(@PathVariable("cid") int cid, @RequestBody CommentVO vo) {
		
		ResponseEntity<String> entity = null;
		try {
			//@SessionAttribute 사용하기는 애매하고 hidden으로 받아오면 client 변조가 될 수 있어서 더 복잡해짐
			vo.setUserid(service.getComment(cid).getUserid());
			vo.setCid(cid);
			service.updateComment(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@DeleteMapping(value="")
	public ResponseEntity<String> deleteComment(@RequestBody CommentVO vo) {
		
		ResponseEntity<String> entity = null;
		
		try {
			//@SessionAttribute 사용하기는 애매하고 hidden으로 받아오면 client 변조가 될 수 있어서 더 복잡해짐
			vo.setUserid(service.getComment(vo.getCid()).getUserid());
			service.deleteComment(vo);
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
			int commentNum = service.countComment(bid);
			entity = new ResponseEntity<String>(commentNum+"", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}
