package com.jaesay.ecommerce.controller.account;

import com.jaesay.ecommerce.domain.dto.SignUpForm;
import com.jaesay.ecommerce.domain.entity.account.Account;
import com.jaesay.ecommerce.service.account.AccountService;
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

    @GetMapping("/signup")
    public String signUp(SignUpForm signUpForm) {
        return "/content/account/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid SignUpForm signUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/content/account/signup";
        }

        Account account = modelMapper.map(signUpForm, Account.class);
        accountService.saveAccount(account);

        return "/content/account/signup-complete";
    }

    @GetMapping("/login")
    public String login() {
        return "/content/account/login";
    }

}
