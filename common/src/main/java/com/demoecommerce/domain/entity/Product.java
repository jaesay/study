package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "productId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
