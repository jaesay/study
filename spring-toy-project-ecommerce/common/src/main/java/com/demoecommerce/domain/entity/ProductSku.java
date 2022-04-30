package com.demoecommerce.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

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

    private String introduction;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private String icon;

    private String images;

    private BigInteger extraPrice;

    private int stock;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

}
