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

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "accountId")
//    private Account account;
    private Long accountId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "cartId")
    private List<CartProduct> cartProducts;

    @Transient
    public BigDecimal getTotalPrice() {
        return cartProducts.stream()
                .map(cartProduct -> cartProduct.getSubPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
