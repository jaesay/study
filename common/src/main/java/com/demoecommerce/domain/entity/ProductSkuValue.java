package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "productSkuValueId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSkuValue {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productSkuValueId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ProductSku.class)
    @JoinColumn(name = "productSkuId")
    private ProductSku productSku;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ProductOptionValue.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "productOptionValueId")
    private ProductOptionValue ProductOptionValue;
}
