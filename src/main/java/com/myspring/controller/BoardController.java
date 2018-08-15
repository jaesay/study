package com.myspring.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.domain.BoardVO;
import com.myspring.domain.PageVO;
import com.myspring.domain.PaginationVO;
import com.myspring.service.BoardService;

@Controller
@RequestMapping("/board/*")
@SessionAttributes("board")
public class BoardController {
	@Inject
	private BoardService service;
	private String uriPrfix = "/board";
	
	@GetMapping("/getBoardList.do")
	public String getBoardList(PageVO pageVO, Model model) throws Exception {
		model.addAttribute("boardList", service.getBoardList(pageVO));
		
		PaginationVO paginationVO = new PaginationVO();
		paginationVO.setPageVO(pageVO);
		paginationVO.setTotalCount(service.countBoard(pageVO));
		model.addAttribute("pagination", paginationVO);
		
		return uriPrfix + "/getBoardList";
	}
	
	@GetMapping("/insertBoard.do")
	public String insertBoardGet(BoardVO vo, @ModelAttribute("page") PageVO pageVO) {
		//model.addAttribute(new BoardVO());
		return uriPrfix + "/insertBoard";
	}
	
	@PostMapping("/insertBoard.do")
	public String insertBoardPost(@Valid BoardVO vo, BindingResult result, @ModelAttribute("page") PageVO pageVO) throws Exception {
		
		if(result.hasErrors()) {
			return uriPrfix + "/insertBoard";
		}
		
		int bid = service.insertBoard(vo);
		return "redirect:" + uriPrfix + "/getBoard.do?bid=" + bid;
	}
	
	@GetMapping("/updateBoard.do")
	public String updateBoardGet(@RequestParam("bid") int bid, @ModelAttribute("page") PageVO pageVO, Model model) throws Exception {
		model.addAttribute("board", service.getBoard(bid));
		return uriPrfix + "/updateBoard";
	}
	
	@PostMapping("/updateBoard.do")
	public String updateBoardPost(@ModelAttribute("board") @Valid  BoardVO vo, BindingResult result, @ModelAttribute("page") PageVO pageVO, RedirectAttributes rttr, SessionStatus sessionStatus) throws Exception {
		if(result.hasErrors()) {
			return uriPrfix + "/updateBoard";
		}
		
		int bid = service.updateBoard(vo);
		
		sessionStatus.setComplete();
		
		rttr.addAttribute("page", pageVO.getPage());
		rttr.addAttribute("perPageNum", pageVO.getPerPageNum());
		
		return "redirect:" + uriPrfix +"/getBoard.do?bid=" + bid;
	}
	
	@PostMapping("/deleteBoard.do")
	public String deleteBoardPost(@RequestParam("bid") int bid, PageVO pageVO, RedirectAttributes rttr) throws Exception {
		service.deleteBoard(bid);
		
		rttr.addAttribute("page", pageVO.getPage());
		rttr.addAttribute("perPageNum", pageVO.getPerPageNum());
		
		return "redirect:" + uriPrfix +"/getBoardList.do";
	}
	
	@GetMapping("/getBoard.do")
	public String getBoardPost(@RequestParam("bid") int bid, @ModelAttribute("page") PageVO pageVO, Model model) throws Exception {
		model.addAttribute("board", service.getBoard(bid));
		return uriPrfix +"/getBoard";
	}
	
}
