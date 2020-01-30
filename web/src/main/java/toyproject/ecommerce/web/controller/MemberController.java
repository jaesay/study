package toyproject.ecommerce.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {

    @GetMapping("/members/new")
    public String createForm(Model model) {
        return "members/createMemberForm";
    }
}
