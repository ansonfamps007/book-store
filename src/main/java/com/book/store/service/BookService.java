
package com.book.store.service;

import java.util.List;

import com.book.store.bean.BookForm;
import com.book.store.dto.BookDto;

public interface BookService {

	void addBook(BookForm bookForm);

	List<BookDto> getAllBooks(int pageNo, int pageLimit);

	List<BookDto> getBookByName(String name);

	void updateBook(BookForm bookForm);

	/*
	 * 
	 * void deleteBook(int id);
	 * 
	 * 
	 */

}
