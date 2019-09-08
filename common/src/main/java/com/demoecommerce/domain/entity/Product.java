package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "productId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    private Category category;

    private String introduction;

    private BigInteger price;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private String icon;

    private String images;

    private Boolean forSale;

    private Boolean onSale;

}
