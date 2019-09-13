package com.demoecommerce.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "cartId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private Long accountId;

//    @JsonSerialize(using = CartProductSerializer.class)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "cartId")
    @JsonIgnore
    private List<CartProduct> cartProducts;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Transient
    @JsonIgnore
    public BigInteger getTotalPrice() {
        return cartProducts.stream()
                .map(cartProduct -> cartProduct.getSubPrice())
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

}
