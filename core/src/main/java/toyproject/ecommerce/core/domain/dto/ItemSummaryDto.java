package toyproject.ecommerce.core.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ItemSummaryDto {

    private String name;
    private int price;
    private int stockQuantity;
    private String picture;
    private String categoryName;

    @QueryProjection
    public ItemSummaryDto(String name, int price, int stockQuantity, String picture, String categoryName) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.picture = picture;
        this.categoryName = categoryName;
    }
}
