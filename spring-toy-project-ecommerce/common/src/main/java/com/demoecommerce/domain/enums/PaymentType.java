package com.demoecommerce.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PaymentType {
    PAID("결제"), REFUND("환불"), CANCEL("취소");

    @Getter
    private String value;
}
