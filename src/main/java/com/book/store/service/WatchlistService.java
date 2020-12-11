package com.book.store.service;

import java.util.List;

import com.book.store.model.Watch;
import com.book.store.response.BookResponse;

public interface WatchlistService {

	boolean existsByBookId(int bookId);
	
	void addToWatchlist(int bookId,String userName);

	int removeFromWatchlist(int bookId, String userName);

	List<BookResponse> getWatchlistedBooks(String userName);

	boolean isWatchListed(int bookId, int userId);

	List<Watch> getWatchlist(int bookId);

	void returnNotification(int bookId);

}
