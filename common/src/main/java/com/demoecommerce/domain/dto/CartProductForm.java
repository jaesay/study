package com.demoecommerce.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartProductForm {

    private Long productSkuId;
    private int quantity;
}
