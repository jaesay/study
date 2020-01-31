package toyproject.ecommerce.web.controller.dto;

import lombok.Getter;
import lombok.Setter;
import toyproject.ecommerce.core.domain.member.Role;
import toyproject.ecommerce.core.support.EqualsPropertyValues;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@EqualsPropertyValues(property = "password", comparingProperty = "passwordConfirm")
public class MemberForm {

    @NotBlank
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    private String password;

    @NotBlank
    @Size(min = 4, max = 20)
    private String passwordConfirm;

    @NotBlank
    private String name;

    private Role role = Role.USER;
}
