package toyproject.ecommerce.web.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCartItemResponseDto {

    private Long cartId;
    private Long itemId;
    private String itemName;
    private int itemCnt;
    private int itemPrice;

    @Builder
    public AddCartItemResponseDto(Long cartId, Long itemId, String itemName, int itemCnt, int itemPrice) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCnt = itemCnt;
        this.itemPrice = itemPrice;
    }
}
