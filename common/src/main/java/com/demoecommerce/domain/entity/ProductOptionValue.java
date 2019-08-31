package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "productOptionValueId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOptionValueId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ProductOption.class)
    @JoinColumn(name = "productOptionId")
    private ProductOption productOption;

    private String optionValue;
}
