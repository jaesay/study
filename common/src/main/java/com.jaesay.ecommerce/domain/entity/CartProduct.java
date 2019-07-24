package com.jaesay.ecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "cartId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class CartProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartProductId;

    private Integer cartId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Transient
    public BigDecimal getTotalPrice() {
        return getProduct().getPrice()
                .multiply(BigDecimal.valueOf(getQuantity()));
    }
}
