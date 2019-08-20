package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "orderProductId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class OrderProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    private Integer orderId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Transient
    public BigDecimal getTotalPrice() {
        return getProduct().getPrice()
                .multiply(BigDecimal.valueOf(getQuantity()));
    }
}
