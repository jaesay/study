package toyproject.ecommerce.web.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class AddCartItemRequestDto {

    @Positive
    private Long itemId;

    @Positive(message = "{cart.add.item.count.error}")
    private int itemCnt;

    @Builder
    public AddCartItemRequestDto(Long itemId, int itemCnt) {
        this.itemId = itemId;
        this.itemCnt = itemCnt;
    }
}
