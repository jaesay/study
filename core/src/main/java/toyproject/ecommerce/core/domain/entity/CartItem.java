package toyproject.ecommerce.core.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class CartItem extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int count;

    public static CartItem createCartItem(Item item, int count) {
        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setCount(count);

        return cartItem;
    }

    public int getTotalPrice() {
        return item.getPrice() * getCount();
    }

    public void deleteCartItem() {
        setCart(null);
    }
}