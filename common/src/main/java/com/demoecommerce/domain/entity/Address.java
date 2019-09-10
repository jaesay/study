package com.demoecommerce.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "addressId")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String addressName;

    private String postcode;

    private String address;

    private String detailAddress;

    private String extraAddress;

    private Boolean isDefault;

}
