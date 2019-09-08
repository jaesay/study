package com.demoecommerce.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigInteger;

@Data
public class PayRequestDto {

    @NotEmpty
    private String impUid;

    @PositiveOrZero
    @NotNull
    private BigInteger paidAmount;

    @NotEmpty
    private String buyerAdrress;

    @NotEmpty
    private String buyerTel;

}
