package toyproject.ecommerce.web.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class DeleteCartItemResponseDto {

    private Long itemId;
    private String itemName;
    private int totalPrice;

    @Builder
    public DeleteCartItemResponseDto(Long itemId, String itemName, int totalPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.totalPrice = totalPrice;
    }
}
