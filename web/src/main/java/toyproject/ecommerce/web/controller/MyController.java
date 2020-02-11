package toyproject.ecommerce.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import toyproject.ecommerce.core.domain.Order;
import toyproject.ecommerce.core.domain.dto.OrderListSummaryDto;
import toyproject.ecommerce.core.domain.dto.OrderSearch;
import toyproject.ecommerce.web.config.oauth.LoginUser;
import toyproject.ecommerce.web.config.oauth.dto.SessionUser;
import toyproject.ecommerce.web.service.OrderService;

@RequiredArgsConstructor
@Controller
public class MyController {

    private final OrderService orderService;

    @GetMapping("/my/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,
                            @LoginUser SessionUser sessionUser,
                            @PageableDefault Pageable pageable,
                            Model model) {

        orderSearch.setEmail(sessionUser.getEmail());
        Page<OrderListSummaryDto> orders = orderService.findOrders(orderSearch, pageable);

        model.addAttribute("orders", orders);

        return "orderList";
    }
}
