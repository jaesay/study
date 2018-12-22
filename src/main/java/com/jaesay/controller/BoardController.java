package com.jaesay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

	@GetMapping("/details")
	public void viewBoard() {
		
	}
	
	@GetMapping("/list")
	public void viewBoardList() {
		
	}
	
	@GetMapping("/new")
	public void writeBoard() {
		
	}
}