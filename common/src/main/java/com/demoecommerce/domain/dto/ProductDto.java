package com.demoecommerce.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.math.BigInteger;

@Data
public class ProductDto {

    private Long productId;

    private String productName;

    private String introduction;

    private BigInteger price;

    private String description;

    private String icon;

    private String images;

    private Boolean forSale;

    private Boolean onSale;

    private String categoryId;

    private String categoryName;

    private Long productOptionId;

    private String optionName;

    private Long productOptionValueId;

    private String optionValue;

    @QueryProjection
    public ProductDto(Long productId, String productName, String introduction, BigInteger price, String description, String icon, String images, Boolean forSale, Boolean onSale, String categoryId, String categoryName, Long productOptionId, String optionName, Long productOptionValueId, String optionValue) {
        this.productId = productId;
        this.productName = productName;
        this.introduction = introduction;
        this.price = price;
        this.description = description;
        this.icon = icon;
        this.images = images;
        this.forSale = forSale;
        this.onSale = onSale;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productOptionId = productOptionId;
        this.optionName = optionName;
        this.productOptionValueId = productOptionValueId;
        this.optionValue = optionValue;
    }
}
