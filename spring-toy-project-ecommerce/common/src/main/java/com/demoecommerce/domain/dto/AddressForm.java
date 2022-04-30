package com.demoecommerce.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data @Builder
public class AddressForm {

    @NotBlank @Max(20)
    private String addressName;

    @NotBlank @Max(20)
    private String postcode;

    @NotBlank @Max(100)
    private String address;

    @NotBlank @Max(100)
    private String detailAddress;

    private String extraAddress;

    @Builder.Default
    private Boolean isDefault = false;

}
