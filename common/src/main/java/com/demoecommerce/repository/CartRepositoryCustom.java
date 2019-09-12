package com.demoecommerce.repository;

import com.demoecommerce.domain.dto.CartSummaryDto;
import com.demoecommerce.domain.entity.Cart;

import java.util.List;

public interface CartRepositoryCustom {
    Cart findCart();

    List<CartSummaryDto> getCartWithCartProducts(Long accountId, Long cartId);
}
