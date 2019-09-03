package com.demoecommerce.web.service;

import com.demoecommerce.domain.entity.Cart;
import com.demoecommerce.domain.entity.CartProduct;
import com.demoecommerce.repository.CartProductRepository;
import com.demoecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartProductRepository cartProductRepository;

    public void saveCartProduct(List<CartProduct> cartProducts) {
        cartProductRepository.saveAll(cartProducts);
    }

    public Optional<Cart> getCart(Long accountId, String cartCookie, HttpServletResponse response) {
        if (accountId > 0) {
            return cartRepository.findByAccountId(accountId);
        } else {
            if (cartCookie != null) {
                return cartRepository.findById(Long.parseLong(cartCookie));
            } else {
                Cart cart = this.save(0L);
                Cookie cartId = new Cookie("mycart", cart.getCartId().toString());
                response.addCookie(cartId);
                return Optional.ofNullable(cart);
            }
        }
    }

    public List<CartProduct> getCartProducts(Long cartId) {
        return cartProductRepository.findByCartId(cartId);
    }

    public Cart save(Long accountId) {
        Cart cart = Cart.builder()
                .accountId(accountId)
                .build();

        return cartRepository.save(cart);
    }
}
