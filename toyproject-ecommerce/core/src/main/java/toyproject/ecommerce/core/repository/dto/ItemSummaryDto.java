package toyproject.ecommerce.core.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import toyproject.ecommerce.core.domain.entity.CartItem;

import java.util.List;

@Data
public class ItemSummaryDto {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String picture;
    private String categoryName;
    private boolean inCart;

    @QueryProjection
    public ItemSummaryDto(Long id, String name, int price, int stockQuantity, String picture, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.picture = picture;
        this.categoryName = categoryName;
    }

    public void checkCartItem(List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            if (this.id.equals(cartItem.getItem().getId())) {
                this.inCart = true;
                break;
            }
        }
    }
}
