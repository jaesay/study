package com.demoecommerce.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

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

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Transient
    public BigInteger getSubPrice() {
        return this.getProductSku().getProduct().getPrice()
                .multiply(BigInteger.valueOf(getQuantity()));
    }
}
