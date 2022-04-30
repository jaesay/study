package com.demoecommerce.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PaymentMethod {
    CARD("신용카드"), TRANS("실시간계좌이체"), VBANK("가상계좌"), PHONE("휴대폰소액결제");

    @Getter
    private String value;
}
