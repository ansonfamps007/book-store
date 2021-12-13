
package com.book.store.bean;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordForm {

	@JsonAlias("email")
	private String username;

	private String password;

	private String resetToken;
}
