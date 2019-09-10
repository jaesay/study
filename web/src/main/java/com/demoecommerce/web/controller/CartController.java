package com.demoecommerce.web.controller;

import com.demoecommerce.domain.dto.CartProductForm;
import com.demoecommerce.domain.entity.Cart;
import com.demoecommerce.domain.entity.CartProduct;
import com.demoecommerce.domain.entity.CustomUserDetails;
import com.demoecommerce.domain.entity.ProductSku;
import com.demoecommerce.web.exception.ResourceNotFoundException;
import com.demoecommerce.web.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @RequestMapping(
            value="/carts",
            method= RequestMethod.POST,
            consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity saveCartProducts(@RequestBody List<CartProductForm> cartProductForms,
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                           @CookieValue(value = "mycart", required = false) String cartCookie,
                                           HttpServletResponse response) {

        Cart cart = cartService.getCart((customUserDetails != null) ? customUserDetails.getAccount().getAccountId() : 0L, cartCookie, response)
                .orElseThrow(ResourceNotFoundException::new);

//        List<Long> skuIds = cartProductForms.stream().map(CartProductForm::getProductSkuId)
//                .collect(Collectors.toList());
//        Cart cart1 = cartService.getCartWithProductsAlreadyIncluded((customUserDetails != null) ? customUserDetails.getAccount().getAccountId() : 0L, cartCookie, response, skuIds);

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

    @GetMapping("/carts")
    public String list(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model,
                       @CookieValue(value = "mycart", required = false) String cartCookie,
                       HttpServletResponse response) {

        Cart cart = cartService.getCart((customUserDetails != null) ? customUserDetails.getAccount().getAccountId() : 0L, cartCookie, response)
                .orElseThrow(ResourceNotFoundException::new);

        List<CartProduct> cartProducts = cartService.getCartProducts(cart.getCartId());
        model.addAttribute("cart", cart);
        model.addAttribute("cartProducts", cartProducts);

        return "/content/carts/list";
    }

}
