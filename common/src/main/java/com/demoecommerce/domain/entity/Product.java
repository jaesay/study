package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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

    private BigDecimal price;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private String icon;

    private String images;

    private Boolean forSale;

    private Boolean onSale;


    /*@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    private Category category;

    private String productName;

    private String introduction;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private BigDecimal price;

    private String imageUrl;

    private Boolean isOnSale;

    private int quantity;*/

}
