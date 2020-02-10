package toyproject.ecommerce.web.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class OrderResponseDto {

    private Long orderId;

    @Builder
    public OrderResponseDto(Long orderId) {
        this.orderId = orderId;
    }
}
