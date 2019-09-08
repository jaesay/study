package com.demoecommerce.web.common;

import com.demoecommerce.domain.entity.Order;
import com.demoecommerce.web.controller.OrderController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class OrderResource extends Resource<Order> {
    public OrderResource(Order content, Link... links) {
        super(content, links);
        linkTo(OrderController.class).slash("process").withSelfRel();
    }
}
