package com.book.store.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemRecords {

	private String itemRecord;

	private String locationId;
	
	private String itemCategory;
	
	private String itemDetails;

	private double price;

	private int quantity;

	private double minPrice;

	private double maxPrice;
}
