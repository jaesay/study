package toyproject.ecommerce.core.domain.entity;

import lombok.*;
import toyproject.ecommerce.core.exception.NotEnoughStockException;

import javax.persistence.*;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    private String picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("Stock is not enough.");
        }
        this.stockQuantity = restStock;
    }

    public Item update(String picture, Category category) {
        this.picture = picture;
        this.category = category;

        return this;
    }
}
