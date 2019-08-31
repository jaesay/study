package com.demoecommerce.domain.entity;

import com.demoecommerce.domain.enums.OrderStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "orderId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    /*@Transient
    public BigDecimal getTotalOrderPrice() {
        return orderProducts.stream()
                .map(orderProduct -> orderProduct.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }*/

}
