package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.repository.dto.OrderListSummaryDto;
import toyproject.ecommerce.core.repository.dto.OrderSearch;
import toyproject.ecommerce.core.domain.entity.Address;
import toyproject.ecommerce.core.domain.entity.Cart;
import toyproject.ecommerce.core.domain.entity.Delivery;
import toyproject.ecommerce.core.domain.entity.Order;
import toyproject.ecommerce.core.domain.enums.OrderStatus;
import toyproject.ecommerce.core.repository.OrderRepository;
import toyproject.ecommerce.web.api.dto.OrderResponseDto;
import toyproject.ecommerce.web.config.auth.dto.SessionUser;

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

    public Page<OrderListSummaryDto> findOrders(OrderSearch orderSearch, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        Page<OrderListSummaryDto> orders = orderRepository.search(orderSearch, pageable)
                .map(OrderListSummaryDto::toDto);

        return orders;
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findByIdAndStatus(orderId, OrderStatus.ORDER)
                .orElseThrow(IllegalStateException::new);

        order.cancel();
    }
}
