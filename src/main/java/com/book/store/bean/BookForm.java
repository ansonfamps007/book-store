
package com.book.store.bean;


import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookForm {

	private int page;

	private int id;

	private Boolean isDonated;

	private String coverUrl;

	private String coverThumbUrl;

	@JsonProperty("title")
	private String title;

	private String feedType;

	@JsonProperty("author")
	private String author;
	
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("language")
	private String language;

	@JsonProperty("category_id")
	private int categoryId;

	@JsonProperty("language_id")
	private int languageId;

	private int authorId;

	private String status;

	private String keyWord;

	private String description;
	
	private MultipartFile coverImage;
	
	@JsonProperty("donation_period_in_months")
	private int noOfMonths;
}
