package com.demoecommerce.support;

import com.demoecommerce.domain.dto.CartSummaryDto;

import java.math.BigInteger;
import java.util.List;

public class PriceUtil {

    public static BigInteger getCartTotalPrice(List<CartSummaryDto> cartSummaryDtos) {
        return cartSummaryDtos.stream()
                .map(cartSummaryDto -> cartSummaryDto.getSubTotalPrice())
                .reduce(BigInteger.ZERO, BigInteger::add);
    }
}
