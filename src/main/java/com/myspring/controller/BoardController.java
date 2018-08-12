package com.myspring.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myspring.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Inject
	private BoardService service;
	
	@GetMapping("/list.do")
	public String getBoardList(Model model) throws Exception {
		model.addAttribute("boardList", service.getAll());
		return "list";
	}
}
