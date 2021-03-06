package com.book.store.bean;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryForm {

	private int id;

	@NotBlank(message = "Category name should not be blank")
	private String name;

}
