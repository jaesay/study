package com.demoecommerce.web.controller;

import com.demoecommerce.domain.dto.CartProductForm;
import com.demoecommerce.domain.entity.Account;
import com.demoecommerce.domain.entity.Cart;
import com.demoecommerce.domain.entity.CartProduct;
import com.demoecommerce.domain.entity.ProductSku;
import com.demoecommerce.support.annotation.CurrentUser;
import com.demoecommerce.web.exception.ResourceNotFoundException;
import com.demoecommerce.web.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/carts")
    public String list(@CurrentUser Account account, Model model,
                       @CookieValue(value = "mycart", required = false) String cartCookie,
                       HttpServletResponse response) {

        Cart cart = cartService.getCart((account != null) ? account.getAccountId() : 0L, cartCookie, response)
                .orElseThrow(ResourceNotFoundException::new);

        List<CartProduct> cartProducts = cartService.getCartProducts(cart.getCartId());
        model.addAttribute("cart", cart);
        model.addAttribute("cartProducts", cartProducts);

        return "/content/carts/list";
    }

    @RequestMapping(
            value="/carts",
            method= RequestMethod.POST,
            consumes="application/json",
            produces="application/json")
    @ResponseBody
    public ResponseEntity saveCartProducts(@RequestBody List<CartProductForm> cartProductForms,
                                           @CurrentUser Account account,
                                           @CookieValue(value = "mycart", required = false) String cartCookie,
                                           HttpServletResponse response) {

        Cart cart = cartService.getCart((account != null) ? account.getAccountId() : 0L, cartCookie, response)
                .orElseThrow(ResourceNotFoundException::new);

        List<CartProduct> cartProducts = new ArrayList<>();
        cartProductForms.forEach(cartProductForm -> cartProducts.add(
                CartProduct.builder()
                        .cartId(cart.getCartId())
                        .productSku(ProductSku.builder().productSkuId(cartProductForm.getProductSkuId()).build())
                        .quantity(cartProductForm.getQuantity())
                        .build()));

        cartService.saveCartProduct(cartProducts);

        return ResponseEntity.ok().build();
    }

}
