package com.demoecommerce.domain.entity;

import com.demoecommerce.domain.adapter.CartProductSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

//    @JsonSerialize(using = CartProductSerializer.class)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "cartId")
    @JsonIgnore
    private List<CartProduct> cartProducts;

    @Transient
    @JsonIgnore
    public BigInteger getTotalPrice() {
        return cartProducts.stream()
                .map(cartProduct -> cartProduct.getSubPrice())
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

}
