
package com.book.store.bean;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginForm {

	@JsonAlias("email")
	private String username;

	private String password;

	private String fcmId;

}
