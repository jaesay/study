package toyproject.ecommerce.core.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.*;
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

        Address address =  new Address("city1", "street1", "123456");
        Delivery delivery = new Delivery();
        delivery.setAddress(address);

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

        OrderItem orderItem1 = OrderItem.createOrderItem(item1, item1.getPrice(), 1);
        OrderItem orderItem2 = OrderItem.createOrderItem(item2, item2.getPrice(), 2);

        Order order = Order.createOrder(user1, delivery, orderItem1, orderItem2);

        //when
        Order savedOrder = orderRepository.save(order);

        //then
        assertThat(order.getCreatedDate()).isAfter(now);
        assertThat(order.getModifiedDate()).isAfter(now);
    }
}