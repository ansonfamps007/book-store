
package com.book.store.service;

import java.util.List;

import com.book.store.response.BookResponse;

public interface BookIssueService {

	String takeRenewBook(int bookId, String userName, boolean b);

	int returnBook(int bookId, String userName);
	
	List<BookResponse> getMyBooks(String userName);

	void sendReturnNotification();
}
