package com.myspring.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myspring.domain.MemberVO;
import com.myspring.domain.UserDetailsVO;
import com.myspring.service.MemberService;

@Controller
@RequestMapping("/user/*")
public class MemberController {

	@Inject
	private MemberService service;
	
	@GetMapping("/login.do")
	public String viewLoginForm() {
		return "/user/login";
	}
	
	@GetMapping("/signup.do")
	public String viewSignupForm(MemberVO vo) {
		return "/user/signup";
	}
	
	@PostMapping("/signup.do")
	public String signup(@Valid MemberVO vo, BindingResult result) {
		if(result.hasErrors()) {
			return "/user/signup";
		}
		
		service.insertMember(vo);
		return "redirect:/user/success.do";
	}
	
	@GetMapping("/success.do")
	public String success() {
		return "/user/success";
	}
	
	@GetMapping("/view-profile.do")
	public String viewProfile(@AuthenticationPrincipal UserDetailsVO vo,Model model) {
		model.addAttribute("member", vo.getMember());
		return "/user/profile";
	}

}
