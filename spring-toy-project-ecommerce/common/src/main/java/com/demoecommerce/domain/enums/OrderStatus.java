package com.demoecommerce.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderStatus {
    READY("미결제"), PAID("결제완료"), CANCELLED("결제취소"), FAILED("결제실패");

    @Getter
    private String value;
}
