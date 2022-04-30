package com.demoecommerce.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.math.BigInteger;

@Data
public class ProductDto {

    private Long productId;
    private String productName;
    private BigInteger price;
    private Boolean forSale;
    private Boolean onSale;
    private String categoryId;
    private String categoryName;
    private String introduction;
    private String description;
    private String icon;
    private String images;
    private Long productOptionId;
    private String optionName;
    private Long productOptionValueId;
    private String optionValue;

    @QueryProjection
    public ProductDto(Long productId, String productName, BigInteger price, Boolean forSale, Boolean onSale, String categoryId, String categoryName, String introduction, String description, String icon, String images, Long productOptionId, String optionName, Long productOptionValueId, String optionValue) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.forSale = forSale;
        this.onSale = onSale;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.introduction = introduction;
        this.description = description;
        this.icon = icon;
        this.images = images;
        this.productOptionId = productOptionId;
        this.optionName = optionName;
        this.productOptionValueId = productOptionValueId;
        this.optionValue = optionValue;
    }
}
