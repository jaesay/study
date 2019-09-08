package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "cartId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private Long accountId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "cartId")
    private List<CartProduct> cartProducts;

    @Transient
    public BigInteger getTotalPrice() {
        return cartProducts.stream()
                .map(cartProduct -> cartProduct.getSubPrice())
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

}
