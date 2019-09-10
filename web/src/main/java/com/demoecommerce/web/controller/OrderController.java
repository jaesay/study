package com.demoecommerce.web.controller;

import com.demoecommerce.domain.dto.PayRequestDto;
import com.demoecommerce.domain.entity.*;
import com.demoecommerce.web.common.OrderResource;
import com.demoecommerce.web.exception.OrderCartEmptyException;
import com.demoecommerce.web.exception.ResourceNotFoundException;
import com.demoecommerce.web.service.CartService;
import com.demoecommerce.web.service.OrderService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final CartService cartService;

    @GetMapping("/checkout")
    public String checkout(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        Account account = customUserDetails.getAccount();
        Cart cart = cartService.getCart(account.getAccountId(), null, null)
                .orElseThrow(ResourceNotFoundException::new);

        List<CartProduct> cartProducts = cart.getCartProducts();
        if (cartProducts.size() == 0) {
            throw new OrderCartEmptyException();
        }

        model.addAttribute("cart", cart);
        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("account", account);

        return "/content/orders/checkout1";
    }

    @GetMapping("/complete")
    public String complete() {
        return "/content/orders/complete";
    }

    @RequestMapping(
            value = "/process",
            method= RequestMethod.POST,
            consumes = "application/json",
            produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity process(@RequestBody @Valid PayRequestDto payRequestDto,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  HttpServletResponse response) throws IOException, IamportResponseException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("false");
        }

        Order order = orderService.process(customUserDetails.getAccount(), payRequestDto, response);

        ControllerLinkBuilder selfLinkBuilder = linkTo(OrderController.class).slash("process");
        URI createdUri = selfLinkBuilder.toUri();
        OrderResource orderResource = new OrderResource(order);
        orderResource.add(linkTo(OrderController.class).slash("checkout").withRel("query-order"));
        orderResource.add(new Link("/docs/temp").withRel("profile"));

        return ResponseEntity.created(createdUri).body(orderResource);
    }
}
