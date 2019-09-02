package com.demoecommerce.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSummaryDto {
    private Long productId;

    private String productName;

    private String introduction;

    private BigDecimal price;

    private String icon;

    private Boolean forSale;

    private Boolean onSale;

    private String categoryId;

    private String categoryName;

    private Long productOptionId;

    private String optionName;

    private Long productOptionValueId;

    private String optionValue;

    @QueryProjection
    public ProductSummaryDto(Long productId, String productName, String introduction, BigDecimal price, String icon, Boolean forSale, Boolean onSale, String categoryId, String categoryName, Long productOptionId, String optionName, Long productOptionValueId, String optionValue) {
        this.productId = productId;
        this.productName = productName;
        this.introduction = introduction;
        this.price = price;
        this.icon = icon;
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
