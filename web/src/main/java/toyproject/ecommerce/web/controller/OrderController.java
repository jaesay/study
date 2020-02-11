package toyproject.ecommerce.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import toyproject.ecommerce.web.service.OrderService;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {

        orderService.cancelOrder(orderId);
        return "redirect:/my/orders";
    }
}
