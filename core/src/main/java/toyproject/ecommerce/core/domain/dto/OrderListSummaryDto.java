package toyproject.ecommerce.core.domain.dto;

import lombok.Getter;
import lombok.Setter;
import toyproject.ecommerce.core.domain.Order;
import toyproject.ecommerce.core.domain.enums.OrderStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class OrderListSummaryDto {

    private Long Id;
    private String itemName;
    private int ItemCount;
    private int totalPrice;
    private OrderStatus status;
    private LocalDateTime orderDate;

    public static OrderListSummaryDto toDto(Order order) {
        OrderListSummaryDto dto = new OrderListSummaryDto();
        dto.setId(order.getId());
        dto.setItemName(order.getOrderItems().get(0).getItem().getName());
        dto.setItemCount(order.getOrderItems().size());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());
        dto.setOrderDate(order.getOrderDate());

        return dto;
    }
}
