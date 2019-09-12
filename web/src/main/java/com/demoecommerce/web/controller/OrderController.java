package com.demoecommerce.web.controller;

import com.demoecommerce.domain.dto.CartSummaryDto;
import com.demoecommerce.domain.dto.PayRequestDto;
import com.demoecommerce.domain.entity.Account;
import com.demoecommerce.domain.entity.CustomUserDetails;
import com.demoecommerce.domain.entity.Order;
import com.demoecommerce.web.exception.OrderCartEmptyException;
import com.demoecommerce.web.exception.ResourceNotFoundException;
import com.demoecommerce.web.service.CartService;
import com.demoecommerce.web.service.OrderService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final CartService cartService;

    @GetMapping("/checkout")
    public String checkout(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        Account account = customUserDetails.getAccount();
        List<CartSummaryDto> cartSummaryDtos = cartService.getCartWithProducts(account.getAccountId(), null);

        if (cartSummaryDtos == null) {
            throw new OrderCartEmptyException();
        }

        model.addAttribute("cartProducts", cartSummaryDtos);
        model.addAttribute("account", account);

        return "/content/orders/checkout1";
    }

    @PostMapping("/complete")
    public String complete(Long orderId, Model model) {
        Order order = orderService.getOrder(orderId)
                .orElseThrow(ResourceNotFoundException::new);

        model.addAttribute("order", order);

        return "/content/orders/complete";
    }

    @RequestMapping(
            value = "/process",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity process(@RequestBody @Valid PayRequestDto payRequestDto,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  HttpServletResponse response) throws IOException, IamportResponseException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Order order = orderService.process(customUserDetails.getAccount(), payRequestDto, response);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
}
