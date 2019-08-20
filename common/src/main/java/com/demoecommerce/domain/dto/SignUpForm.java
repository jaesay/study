package com.demoecommerce.domain.dto;

import com.demoecommerce.support.annotation.EqualsPropertyValues;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsPropertyValues(property = "password", comparingProperty = "rePassword")
public class SignUpForm {

    @NotEmpty
    @Size(min=3, max=20)
    private String accountName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min=3, max=20)
    private String password;

    @NotEmpty
    @Size(min=3, max=20)
    private String rePassword;
}
