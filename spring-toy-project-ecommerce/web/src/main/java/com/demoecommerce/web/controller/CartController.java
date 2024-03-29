package com.demoecommerce.web.controller;

import com.demoecommerce.domain.dto.CartProductForm;
import com.demoecommerce.domain.dto.CartSummaryDto;
import com.demoecommerce.domain.entity.Cart;
import com.demoecommerce.domain.entity.CartProduct;
import com.demoecommerce.domain.entity.CustomUserDetails;
import com.demoecommerce.support.validation.CustomCollectionValidator;
import com.demoecommerce.web.exception.ResourceNotFoundException;
import com.demoecommerce.web.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final CustomCollectionValidator customCollectionValidator;

    @RequestMapping(
            value="/carts",
            method= RequestMethod.POST,
            consumes= MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity saveCartProducts(@RequestBody @Valid List<CartProductForm> cartProductForms,
                                           BindingResult bindingResult,
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                           @CookieValue(value = "mycart", required = false) String cartCookie,
                                           HttpServletResponse response) {

        customCollectionValidator.validate(cartProductForms, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Cart cart = cartService.getCart((customUserDetails != null) ? customUserDetails.getAccount().getAccountId() : 0L, cartCookie, response)
                .orElseThrow(ResourceNotFoundException::new);

        List<CartProduct> newCartProducts = cartService.saveOrUpdateCartProducts(cart.getCartId(), cartProductForms);

        return ResponseEntity.ok(newCartProducts.size());

    }

    @GetMapping("/carts")
    public String list(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model,
                       @CookieValue(value = "mycart", required = false) String cartCookie) {

        List<CartSummaryDto> cartSummaryDtos = cartService.getCartWithProducts((customUserDetails != null) ? customUserDetails.getAccount().getAccountId() : 0L, cartCookie);

        model.addAttribute("cartProducts", cartSummaryDtos);

        return "/content/carts/list";
    }

    @GetMapping("/carts/test")
    @ResponseBody
    public String test() {
        Cart test = cartService.test();
        return test.toString();
    }

}
