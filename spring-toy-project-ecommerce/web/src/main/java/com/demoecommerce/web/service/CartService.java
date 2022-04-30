package com.demoecommerce.web.service;

import com.demoecommerce.domain.dto.CartProductForm;
import com.demoecommerce.domain.dto.CartSummaryDto;
import com.demoecommerce.domain.entity.Cart;
import com.demoecommerce.domain.entity.CartProduct;
import com.demoecommerce.domain.entity.ProductSku;
import com.demoecommerce.repository.CartProductRepository;
import com.demoecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartProductRepository cartProductRepository;

    private final ModelMapper modelMapper;

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

    @Transactional
    public List<CartProduct> saveOrUpdateCartProducts(Long cartId, List<CartProductForm> cartProductForms) {

        List<Long> productSkuIds = cartProductForms.stream()
                .map(CartProductForm::getProductSkuId)
                .collect(Collectors.toList());

        List<CartProduct> sameCartProducts = cartProductRepository.findByCartIdAndProductSku_ProductSkuIdIn(cartId, productSkuIds);

        List<CartProduct> newCartProducts = new ArrayList<>();
        for (CartProductForm newCartProductForm : cartProductForms) {
            boolean isAlreadyIncluded = false;
            for (CartProduct existingCartProduct : sameCartProducts) {
                if (newCartProductForm.getProductSkuId().equals(existingCartProduct.getProductSku().getProductSkuId())) {
                    existingCartProduct.setQuantity(newCartProductForm.getQuantity());
                    isAlreadyIncluded = true;
                }
            }

            if (!isAlreadyIncluded) {
                newCartProducts.add(CartProduct.builder()
                        .cartId(cartId)
                        .productSku(ProductSku.builder().productSkuId(newCartProductForm.getProductSkuId()).build())
                        .quantity(newCartProductForm.getQuantity())
                        .build());
            }
        }

        return cartProductRepository.saveAll(newCartProducts);
    }

    public Cart test() {
        return cartRepository.findCart();

    }

    public List<CartSummaryDto> getCartWithProducts(long accountId, String cartCookie) {

        if (accountId > 0 || cartCookie != null) {
            long cartId = 0L;
            if (cartCookie != null) {
                cartId = Long.parseLong(cartCookie);
            }
            List<CartSummaryDto> cartSummaryDtos = cartRepository.getCartWithCartProducts(accountId, cartId);
            if (cartSummaryDtos.size() == 1 && cartSummaryDtos.get(0).getProductSkuId() == null) {
                return null;
            }
            return cartSummaryDtos;
        }
        return null;
    }
}
