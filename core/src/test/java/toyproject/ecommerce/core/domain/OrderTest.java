package toyproject.ecommerce.core.domain;

import org.junit.Test;
import toyproject.ecommerce.core.domain.entity.*;
import toyproject.ecommerce.core.domain.enums.OrderStatus;
import toyproject.ecommerce.core.domain.enums.Role;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    public void createOrder() {
        //given
        Member user1 = Member.builder()
                .email("user1@test.com")
                .name("user1")
                .role(Role.USER)
                .build();

        Cart cart = Cart.createCart(user1);

        Address address =  new Address("city1", "street1", "123456");
        Delivery delivery = Delivery.createDelivery(address);

        Item item1 = Item.builder()
                .name("item1")
                .price(10)
                .stockQuantity(100)
                .build();

        Item item2 = Item.builder()
                .name("item2")
                .price(20)
                .stockQuantity(200)
                .build();

        cart.addCartItem(item1, 1);
        cart.addCartItem(item2, 2);

        //when
        Order order = Order.createOrder(cart, delivery);

        //then
        assertThat(order.getTotalPrice()).isEqualTo(1 * 10 + 20 *2);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getOrderItems().size()).isEqualTo(2);
    }
}