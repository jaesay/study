package com.myspring.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myspring.domain.BoardVO;
import com.myspring.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Inject
	private BoardService service;
	private String uriPrfix = "/board";
	
	@GetMapping("/getBoardList.do")
	public String getBoardList(Model model) throws Exception {
		model.addAttribute("boardList", service.getBoardList());
		return uriPrfix + "/getBoardList";
	}
	
	@GetMapping("/insertBoard.do")
	public String insertBoardGet() {
		return uriPrfix + "/insertBoard";
	}
	
	@PostMapping("/insertBoard.do")
	public String insertBoardPost(BoardVO vo) throws Exception {
		int bid = service.insertBoard(vo);
		return "redirect:" + uriPrfix + "/getBoard.do?bid=" + bid;
	}
	
	@GetMapping("/updateBoard.do")
	public String updateBoardGet(int bid, Model model) throws Exception {
		model.addAttribute("board", service.getBoard(bid));
		return uriPrfix + "/updateBoard";
	}
	
	@PostMapping("/updateBoard.do")
	public String updateBoardPost(BoardVO vo, Model model) throws Exception {
		int bid = service.updateBoard(vo);
		return "redirect:" + uriPrfix +"/getBoard.do?bid=" + bid;
	}
	
	@PostMapping("/deleteBoard.do")
	public String deleteBoardPost(int bid) throws Exception {
		service.deleteBoard(bid);
		return "redirect:" + uriPrfix +"/getBoardList.do";
	}
	
	@GetMapping("/getBoard.do")
	public String getBoardPost(int bid, Model model) throws Exception {
		model.addAttribute("board", service.getBoard(bid));
		return uriPrfix +"/getBoard";
	}
	
}
