
package com.book.store.service;

import java.util.List;

import com.book.store.bean.AuthorForm;
import com.book.store.dto.AuthorDto;

public interface AuthorService {

	void addAuthor(String name);

	void updateAuthor(AuthorForm authorForm);

	AuthorDto getAuthorByName(String name);

	void deleteAuthor(int id);

	List<AuthorDto> getAllAuthors();

}
