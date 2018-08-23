package com.myspring.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myspring.domain.UserDetailsVO;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(@AuthenticationPrincipal UserDetailsVO userDetails, Model model) {
		if(userDetails != null) {
			model.addAttribute("member", userDetails.getMember());
		}
		return "home";
	}
	
}
