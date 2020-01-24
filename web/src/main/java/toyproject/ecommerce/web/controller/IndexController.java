package toyproject.ecommerce.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import toyproject.ecommerce.core.domain.member.Member;
import toyproject.ecommerce.web.config.oauth.LoginUser;
import toyproject.ecommerce.web.config.oauth.dto.SessionUser;
import toyproject.ecommerce.web.controller.dto.PostsResponseDto;
import toyproject.ecommerce.web.service.PostsService;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("")
    public String index(Model model, @LoginUser SessionUser member) {
        model.addAttribute("posts", postsService.findAllDesc());

        if (member != null) {
            model.addAttribute("member", member);
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
