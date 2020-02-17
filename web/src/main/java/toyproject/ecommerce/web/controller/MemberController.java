package toyproject.ecommerce.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import toyproject.ecommerce.core.domain.entity.Member;
import toyproject.ecommerce.web.controller.dto.MemberForm;
import toyproject.ecommerce.web.service.MemberService;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Validated MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        form.setPassword(passwordEncoder.encode(form.getPassword()));
        Member member = form.toEntity();
        memberService.signUp(member);

        return "redirect:/login";
    }

    @PostMapping("/members/username")
    @ResponseBody
    public Boolean exists(String email) {
        return memberService.isUsernameAvailable(email);
    }
}