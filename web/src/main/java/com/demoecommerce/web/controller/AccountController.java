package com.demoecommerce.web.controller;

import com.demoecommerce.domain.dto.SignUpForm;
import com.demoecommerce.domain.entity.Account;
import com.demoecommerce.web.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final ModelMapper modelMapper;

    @GetMapping("/login")
    public String login() {
        return "/content/accounts/login";
    }

    @GetMapping("/signup")
    public String signUp(SignUpForm signUpForm) {
        return "/content/accounts/signup";
    }

    @PostMapping("/signup/complete")
    public String complete() {
        return "/content/accounts/signup-complete";
    }

    @PostMapping("/accounts")
    public String saveAccount(@Valid SignUpForm signUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/content/accounts/signup";
        }

        Account account = modelMapper.map(signUpForm, Account.class);
        accountService.saveAccount(account);

        return "redirect:/signup/complete";
    }

}
