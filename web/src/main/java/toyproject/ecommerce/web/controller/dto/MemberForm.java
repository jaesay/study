package toyproject.ecommerce.web.controller.dto;

import lombok.Getter;
import lombok.Setter;
import toyproject.ecommerce.core.domain.entity.Member;
import toyproject.ecommerce.core.domain.enums.Role;
import toyproject.ecommerce.core.support.EqualsPropertyValues;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@EqualsPropertyValues(property = "password", comparingProperty = "passwordConfirm")
public class MemberForm {

    @NotBlank
    @Email(message = "{member.form.email.error}")
    private String email;

    @NotBlank
    @Size(min = 4, max = 20, message = "{member.form.password.error}")
    private String password;

    @NotBlank
    @Size(min = 4, max = 20, message = "{member.form.password.error}")
    private String passwordConfirm;

    @NotBlank
    private String name;

    private Role role = Role.USER;

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .role(this.role)
                .build();
    }
}
