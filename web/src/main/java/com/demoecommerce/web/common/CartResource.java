package com.demoecommerce.web.common;

import com.demoecommerce.domain.entity.Cart;
import com.demoecommerce.web.controller.CartController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class CartResource extends Resource<Cart> {
    public CartResource(Cart content, Link... links) {
        super(content, links);
        add(linkTo(CartController.class).slash("/carts").withSelfRel());
    }
}
