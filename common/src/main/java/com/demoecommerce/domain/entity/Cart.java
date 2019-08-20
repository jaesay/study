package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "cartId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartProduct> cartProducts;

    @Transient
    public BigDecimal getTotalOrderPrice() {
        return cartProducts.stream()
                .map(orderProduct -> orderProduct.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
