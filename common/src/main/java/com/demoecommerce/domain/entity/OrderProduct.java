package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "orderProductId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class OrderProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    private Long orderId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_sku_id")
    private ProductSku productSku;

    private int quantity;

    /*@Transient
    public BigDecimal getTotalPrice() {
        return getProduct().getPrice()
                .multiply(BigDecimal.valueOf(getQuantity()));
    }*/
}
