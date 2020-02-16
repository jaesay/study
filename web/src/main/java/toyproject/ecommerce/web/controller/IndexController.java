package toyproject.ecommerce.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import toyproject.ecommerce.core.domain.entity.Cart;
import toyproject.ecommerce.core.repository.dto.ItemSearch;
import toyproject.ecommerce.core.repository.dto.ItemSummaryDto;
import toyproject.ecommerce.web.config.auth.LoginUser;
import toyproject.ecommerce.web.config.auth.dto.SessionUser;
import toyproject.ecommerce.web.service.CartService;
import toyproject.ecommerce.web.service.ItemService;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ItemService itemService;
    private final CartService cartService;

    @GetMapping("")
    public String index(@ModelAttribute("itemSearch") ItemSearch itemSearch,
                        @PageableDefault(value = 9) Pageable pageable,
                        @LoginUser SessionUser member,
                        Model model) {

        Cart cart = cartService.getCart(member);
        Page<ItemSummaryDto> items = itemService.findItems(itemSearch, cart, pageable);

        model.addAttribute("items", items);
        model.addAttribute("cart", cart);

        return "index";
    }
}
