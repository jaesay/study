package com.jaesay.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jaesay.domain.Board;
import com.jaesay.domain.CustomUserDetails;
import com.jaesay.form.BoardForm;
import com.jaesay.form.Pagination;
import com.jaesay.service.BoardService;
import com.jaesay.support.common.PaginationHelper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/boards")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/{boardId}")
	public String detail(@PathVariable Long boardId, HttpServletRequest request, Model model) {
		boardService.findById(boardId).ifPresent(board -> model.addAttribute("board", board));
		String queryString = Optional.ofNullable(request.getQueryString()).orElse("");
		model.addAttribute("queryString", queryString);
		
		return "/boards/detail";
	}
	
	@GetMapping("")
	public String list(Pagination pagination, Model model) {
		Pageable page = pagination.getPageable(0, "boardId");
		Page<Board> result = boardService.findAll(boardService.getPredicate(pagination.getType(), pagination.getKeyword()), page);
	
		model.addAttribute("result", new PaginationHelper<>(result));
		
		return "/boards/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/post")
	public String create(Model model) {
		model.addAttribute("boardForm", new BoardForm());
		return "/boards/post";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/post")
	public String create(@Valid BoardForm boardForm , BindingResult result, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

		log.debug("=================BoardForm: " + boardForm.toString());
		if(result.hasErrors()) {
			log.debug("signup error!!!");
			return "/boards/post";
		}

		ModelMapper modelMapper = new ModelMapper();
		Board board = modelMapper.map(boardForm, Board.class);
		board.setMember(customUserDetails.getMember());
		boardService.save(board);
		
		return "redirect:/boards";
	}
	
	@GetMapping("/modify")
	public String update(Long boardId, HttpServletRequest request, Model model) {
		Board board = boardService.findById(boardId).orElse(new Board());
		ModelMapper modelMapper = new ModelMapper();
		BoardForm boardForm = modelMapper.map(board, BoardForm.class);
		model.addAttribute("boardForm", boardForm);
		String queryString = Optional.ofNullable(request.getQueryString()).orElse("");
		model.addAttribute("queryString", queryString);
		
		return "/boards/modify";
	}
	
	@PostMapping("/modify")
	public String update(Long boardId, Model model, @Valid BoardForm boardForm , BindingResult result, RedirectAttributes rttr, HttpServletRequest request) {
		
		log.info("BOARD FORM: " + boardForm.toString());
		String queryString = Optional.ofNullable(request.getQueryString()).orElse("");
		if(result.hasErrors()) {
			log.debug("signup error!!!");
			return "/boards/" + boardId + "?" + queryString;
		}
		
		boardService.findById(boardId).ifPresent(board -> {
			board.setTitle(boardForm.getTitle());
			board.setContent(boardForm.getContent());
			boardService.save(board);
		});
		
		return "redirect:/boards/" + boardId + "?" + queryString;
	}
}