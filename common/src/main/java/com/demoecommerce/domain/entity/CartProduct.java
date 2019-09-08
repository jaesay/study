package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "cartId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class CartProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartProductId;

    private Long cartId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_sku_id")
    private ProductSku productSku;

    private int quantity;

    @Transient
    public BigInteger getSubPrice() {
        return this.getProductSku().getProduct().getPrice()
                .multiply(BigInteger.valueOf(getQuantity()));
    }
}
