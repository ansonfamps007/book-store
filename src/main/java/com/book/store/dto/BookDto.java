package com.book.store.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class BookDto {

	private Integer id;

	private String title;

	private String description;

	private String author;

	private String language;

	private String category;

	//private LocalDateTime createdAt;

}
