package com.demoecommerce.web.controller;

import com.demoecommerce.domain.dto.CartProductForm;
import com.demoecommerce.domain.entity.*;
import com.demoecommerce.support.annotation.CurrentUser;
import com.demoecommerce.web.common.CartResource;
import com.demoecommerce.web.common.OrderResource;
import com.demoecommerce.web.exception.ResourceNotFoundException;
import com.demoecommerce.web.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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
            produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity saveCartProducts(@RequestBody List<CartProductForm> cartProductForms,
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                           @CookieValue(value = "mycart", required = false) String cartCookie,
                                           HttpServletResponse response) {

        Cart cart = cartService.getCart((customUserDetails != null) ? customUserDetails.getAccount().getAccountId() : 0L, cartCookie, response)
                .orElseThrow(ResourceNotFoundException::new);

        List<CartProduct> cartProducts = new ArrayList<>();
        cartProductForms.forEach(cartProductForm -> cartProducts.add(
                CartProduct.builder()
                        .cartId(cart.getCartId())
                        .productSku(ProductSku.builder().productSkuId(cartProductForm.getProductSkuId()).build())
                        .quantity(cartProductForm.getQuantity())
                        .build()));

        cartService.saveCartProduct(cartProducts);
        ControllerLinkBuilder selfLinkBuilder = linkTo(CartController.class);
        URI createdUri = selfLinkBuilder.toUri();
        CartResource cartResource = new CartResource(cart);
        cartResource.add(linkTo(CartController.class).slash("/carts").withRel("query_cart"));
        cartResource.add(new Link("/docs/temp").withRel("profile"));

        ResponseEntity<CartResource> body = ResponseEntity.created(createdUri).body(cartResource);
        return body;
    }

}
