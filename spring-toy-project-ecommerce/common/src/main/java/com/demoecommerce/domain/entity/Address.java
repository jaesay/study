package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "addressId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "account_id")
    private Account account;

    private String addressName;

    private String postcode;

    private String address;

    private String detailAddress;

    private String extraAddress;

    private Boolean isDefault;

}
