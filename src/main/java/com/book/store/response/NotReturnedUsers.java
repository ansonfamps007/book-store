
package com.book.store.response;

import java.time.LocalDateTime;

public interface NotReturnedUsers {

	String getName();

	String getTitle();

	LocalDateTime getReturnDt();

	LocalDateTime getIssuedAt();

	int getDaysToReturn();

}
