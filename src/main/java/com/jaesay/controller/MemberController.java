package com.jaesay.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jaesay.domain.Member;
import com.jaesay.form.SignupForm;
import com.jaesay.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return "/members/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid SignupForm signupForm , BindingResult result) {
		log.info("SIGNUPFORM: " + signupForm);
		
		if(result.hasErrors()) {
			log.debug("signup error!!!");
			return "/members/signup";
		}

		ModelMapper modelMapper = new ModelMapper();
		Member member = modelMapper.map(signupForm, Member.class);
		
		log.info("MEMBER: " + member);
		
		memberService.save(member);
		return "redirect:welcome";
	}
	
	@PreAuthorize("hasRole('ADMIN') or (#memberName == principal.member.memberName)")
	@GetMapping("members/{memberName}")
	public String details(@PathVariable String memberName, Model model) {
		model.addAttribute("member", memberService.findByMemberName(memberName));
		return "members/detail";
	}

}