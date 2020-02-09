package toyproject.ecommerce.core.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

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
}
