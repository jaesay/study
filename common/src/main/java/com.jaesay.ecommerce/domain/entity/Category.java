package com.jaesay.ecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "categoryId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Category {

    @Id
    private String categoryId;

    @Column(nullable = false)
    private String categoryName;
}
