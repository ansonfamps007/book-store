
package com.book.store.bean;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthorForm {

	@NotEmpty
	private String name;

	private int id;

}
