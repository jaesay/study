package com.demoecommerce.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductOptionDto {

//    Long getProductSkuId();
//
//    Long getProductOptionId();
//
//    String getOptionName();
//
//    Long getProductOptionValueId();
//
//    String getOptionValue();

//    Integer getStock();

    private Long productSkuId;
    private Long productOptionId;
    private String optionName;
    private Long productOptionValueId;
    private String optionValue;
    private int stock;
}
