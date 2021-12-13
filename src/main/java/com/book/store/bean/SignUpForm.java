
package com.book.store.bean;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpForm {

	private String name;

	@JsonAlias("email")
	private String username;

	private String password;

	private String role;

	private String fcmId;

	private String refreshToken;
}
