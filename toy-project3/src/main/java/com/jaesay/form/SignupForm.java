package com.jaesay.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jaesay.support.validatior.EqualsPropertyValues;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsPropertyValues(property = "password", comparingProperty = "passwordRepeat")
public class SignupForm {

	@NotNull
	@Size(max = 15, min = 3)
	private String memberName;
	@NotNull
	@Size(max = 128, min = 5)
	private String email;
	@NotNull
	@Size(max = 20, min = 3)
	private String password;
	@NotNull
	@Size(max = 20, min = 3)
	private String passwordRepeat;
}
