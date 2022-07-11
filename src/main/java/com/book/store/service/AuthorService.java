
package com.book.store.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.book.store.bean.AuthorForm;
import com.book.store.dto.AuthorDto;
import com.book.store.model.Author;
import com.book.store.model.Book;

public interface AuthorService {

	void addAuthor(String name);

	void updateAuthor(AuthorForm authorForm);

	AuthorDto getAuthorByName(String name);

	void deleteAuthor(int id);

	List<AuthorDto> getAllAuthors();

	List<String> getBooks(String name);

}
