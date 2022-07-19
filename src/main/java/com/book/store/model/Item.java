package com.book.store.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {

	private String itemId;

	private String itemCategory;

	private String locationId;

	private int quantity;

	private double price;

	private String bookTitle;

	private String bookAuthor;

	private String bookCategory;

	private String bookLanguage;

	private String bookDescription;
}
