package toyproject.ecommerce.core.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.entity.*;
import toyproject.ecommerce.core.domain.enums.Role;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderRepositoryTest {

    @Autowired OrderRepository orderRepository;

    @Test
    public void save() {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
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

        Order order = Order.createOrder(cart, delivery);

        //when
        Order savedOrder = orderRepository.save(order);

        //then
        assertThat(order.getCreatedDate()).isAfter(now);
        assertThat(order.getModifiedDate()).isAfter(now);
    }
}