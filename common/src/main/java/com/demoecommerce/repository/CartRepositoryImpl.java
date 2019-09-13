package com.demoecommerce.repository;

import com.demoecommerce.domain.dto.CartSummaryDto;
import com.demoecommerce.domain.entity.Cart;
import com.demoecommerce.domain.entity.QCart;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.demoecommerce.domain.entity.QCart.cart;
import static com.demoecommerce.domain.entity.QCartProduct.cartProduct;
import static com.demoecommerce.domain.entity.QProductSku.productSku;
import static com.demoecommerce.domain.entity.QProduct.product;
import static com.demoecommerce.domain.entity.QProductOptionValue.productOptionValue;
import static com.demoecommerce.domain.entity.QProductSkuValue.productSkuValue;
import static com.demoecommerce.domain.entity.QProductOption.productOption;

@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Cart findCart() {

        return jpaQueryFactory
                .selectFrom(cart)
                .join(cart.cartProducts, cartProduct).fetchJoin()
                .where(cart.accountId.eq(2L))
                .fetchOne();

    }

    @Override
    public List<CartSummaryDto> getCartWithCartProducts(Long accountId, Long cartId) {
        BooleanBuilder builder = new BooleanBuilder();

        if (accountId > 0) {
            builder.and(cart.accountId.eq(accountId));
        } else {
            builder.and(cart.cartId.eq(cartId));
        }
        builder.and(productOption.productOptionId.eq(2L));

        QueryResults result = jpaQueryFactory
                .select(Projections.constructor(CartSummaryDto.class, cart.cartId, cart.accountId, cartProduct.cartProductId, cartProduct.quantity, productSku.productSkuId, productSku.extraPrice, productSku.sku,
                        productSku.skuName, productSku.icon, productSku.stock, product.productId, product.forSale, product.onSale, product.price, product.productName, productOptionValue.optionValue))
                .from(cart)
                .join(cartProduct).on(cart.cartId.eq(cartProduct.cartId))
                .join(productSku).on(cartProduct.productSku.productSkuId.eq(productSku.productSkuId))
                .innerJoin(product).on(productSku.product.productId.eq(product.productId))
                .innerJoin(productSkuValue).on(productSku.productSkuId.eq(productSkuValue.productSku.productSkuId))
                .innerJoin(productOptionValue).on(productSkuValue.productOptionValue.productOptionValueId.eq(productOptionValue.productOptionValueId))
                .innerJoin(productOption).on(productOptionValue.productOption.productOptionId.eq(productOption.productOptionId))
                .where(builder)
                .orderBy(cartProduct.cartProductId.desc())
                .fetchResults();

        return result.getResults();
    }
}
