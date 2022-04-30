package com.jaesay.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardForm {

	@NotNull
	@Size(max = 255, min = 1)
	private String title;
	@NotNull
	private String content;
}
