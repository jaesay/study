package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.*;
import toyproject.ecommerce.core.repository.OrderRepository;
import toyproject.ecommerce.web.api.dto.OrderResponseDto;
import toyproject.ecommerce.web.config.oauth.dto.SessionUser;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto order(SessionUser sessionUser, Address address) {

        Cart cart = cartService.getCart(sessionUser);
        Delivery delivery = Delivery.createDelivery(address);

        Order order = Order.createOrder(cart, delivery);

        orderRepository.save(order);
        cartService.emptyCartItem(cart);

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .build();
    }
}
