package toyproject.ecommerce.core.domain;

import org.junit.Test;
import toyproject.ecommerce.core.domain.exception.NotEnoughStockException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class ItemTest {

    @Test
    public void addStock() {
        //given
        Item item1 = Item.builder()
                .name("item1")
                .price(10)
                .stockQuantity(100)
                .build();

        //when
        item1.addStock(2);

        //then
        assertThat(item1.getStockQuantity()).isEqualTo(102);
    }

    @Test
    public void removeStock() {
        //given
        Item item1 = Item.builder()
                .name("item1")
                .price(10)
                .stockQuantity(100)
                .build();

        //when
        item1.removeStock(30);

        //then
        assertThat(item1.getStockQuantity()).isEqualTo(70);
    }

    @Test(expected = NotEnoughStockException.class)
    public void removeStockCausesNotEnoughStockException() {
        //given
        Item item1 = Item.builder()
                .name("item1")
                .price(10)
                .stockQuantity(100)
                .build();

        //when
        item1.removeStock(101);

        //then
        fail("NotEnoughStockException has occurred.");
    }
}