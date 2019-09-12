package com.demoecommerce.web.service;

import com.demoecommerce.domain.dto.PayRequestDto;
import com.demoecommerce.domain.entity.Account;
import com.demoecommerce.domain.entity.Cart;
import com.demoecommerce.domain.entity.Order;
import com.demoecommerce.domain.entity.OrderProduct;
import com.demoecommerce.domain.enums.OrderStatus;
import com.demoecommerce.domain.enums.PaymentMethod;
import com.demoecommerce.repository.OrderProductRepository;
import com.demoecommerce.repository.OrderRepository;
import com.demoecommerce.web.exception.ResourceNotFoundException;
import com.demoecommerce.web.service.account.AccountService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final IamportClient client;

    private final AccountService accountService;

    private final CartService cartService;

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    @Transactional
    public Order process(Account account, PayRequestDto payRequestDto, HttpServletResponse response) throws IOException, IamportResponseException {
        Cart cart = cartService.getCart(account.getAccountId(), null, response)
                .orElseThrow(ResourceNotFoundException::new);

        Payment paymentResult = getPaymentResult(payRequestDto);

        if (paymentResult == null) {
            return null; // 사용자가 결제를 취소
        }

        Order order = saveOrder(cart, paymentResult);

        if (paymentResult.getStatus().equalsIgnoreCase(OrderStatus.PAID.toString())
                && !payRequestDto.getPaidAmount().equals(cart.getTotalPrice())) {
            cancelOrder(order, paymentResult);
        }

        return order;
    }

    public Payment getPaymentResult(PayRequestDto payRequestDto) throws IOException, IamportResponseException {
        IamportResponse<Payment> paymentIamportResponse = client.paymentByImpUid(payRequestDto.getImpUid());

        Payment paymentResult = paymentIamportResponse.getResponse();

        return paymentResult;
    }

    public Payment cancelOrder(Order order, Payment paymentResult) throws IOException, IamportResponseException {
        IamportResponse<Payment> paymentIamportResponse = client.cancelPaymentByImpUid(new CancelData(paymentResult.getImpUid(), true));
        Payment payment = paymentIamportResponse.getResponse();

        order.setOrderStatus(OrderStatus.valueOf(payment.getStatus().toUpperCase()));

        return payment;
    }

    public Order saveOrder(Cart cart, Payment paymentResult) {

        Account account = accountService.getAccount(cart.getAccountId())
                .orElseThrow(ResourceNotFoundException::new);

        com.demoecommerce.domain.entity.Payment payment = com.demoecommerce.domain.entity
                .Payment.builder()
                .paymentMethod(PaymentMethod.valueOf(paymentResult.getPayMethod().toUpperCase()))
                .paidAmount(paymentResult.getAmount().toBigInteger())
                .description(paymentResult.getName())
                .build();

        Order order = Order.builder()
                .account(account)
                .orderStatus(OrderStatus.valueOf(paymentResult.getStatus().toUpperCase()))
                .payment(payment)
                .build();

        Order savedOrder = orderRepository.save(order);

        List<OrderProduct> orderProducts = cart.getCartProducts().stream()
                .map(cartProduct -> OrderProduct.builder()
                        .orderId(savedOrder.getOrderId())
                        .productSku(cartProduct.getProductSku())
                        .quantity(cartProduct.getQuantity())
                        .build())
                .collect(Collectors.toList());

        orderProductRepository.saveAll(orderProducts);

        return order;
    }

    public Optional<Order> getOrder(Long orderId) {
        return orderRepository.findById(orderId);
    }
}
