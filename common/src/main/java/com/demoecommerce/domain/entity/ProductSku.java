package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "productSkuId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productSkuId;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Product.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "productId")
    private Product product;

    private String skuName;

    private String sku;

    private BigDecimal extraPrice;

    private int stock;

}
