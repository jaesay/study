package toyproject.ecommerce.core.repository.dto;

import lombok.Getter;
import lombok.Setter;
import toyproject.ecommerce.core.domain.enums.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String email;
    private OrderStatus orderStatus;
}
