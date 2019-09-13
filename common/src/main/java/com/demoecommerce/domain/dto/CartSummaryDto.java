package com.demoecommerce.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.math.BigInteger;

@Data
public class CartSummaryDto {

    private Long cartId;
    private Long accountId;
    private Long cartProductId;
    private int quantity;
    private Long productSkuId;
    private BigInteger extraPrice;
    private String sku;
    private String skuName;
    private String icon;
    private int stock;
    private Long productId;
    private Boolean forSale;
    private Boolean onSale;
    private BigInteger price;
    private String productName;
    private String optionValue;

    @QueryProjection
    public CartSummaryDto(Long cartId, Long accountId, Long cartProductId, int quantity, Long productSkuId, BigInteger extraPrice, String sku, String skuName, String icon, int stock, Long productId, Boolean forSale, Boolean onSale, BigInteger price, String productName, String optionValue) {
        this.cartId = cartId;
        this.accountId = accountId;
        this.cartProductId = cartProductId;
        this.quantity = quantity;
        this.productSkuId = productSkuId;
        this.extraPrice = extraPrice;
        this.sku = sku;
        this.skuName = skuName;
        this.icon = icon;
        this.stock = stock;
        this.productId = productId;
        this.forSale = forSale;
        this.onSale = onSale;
        this.price = price;
        this.productName = productName;
        this.optionValue = optionValue;
    }

    public BigInteger getSubTotalPrice() {
        return this.price.add(this.extraPrice)
                .multiply(BigInteger.valueOf(this.quantity));
    }
}
