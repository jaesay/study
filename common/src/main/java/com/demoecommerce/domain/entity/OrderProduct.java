package com.demoecommerce.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    /*@Transient
    public BigDecimal getTotalPrice() {
        return getProduct().getPrice()
                .multiply(BigDecimal.valueOf(getQuantity()));
    }*/
}
