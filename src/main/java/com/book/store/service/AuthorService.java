
package com.book.store.service;

import java.util.List;
import java.util.Optional;

import com.book.store.bean.AuthorForm;
import com.book.store.model.Author;
import com.book.store.response.AuthorResponse;

public interface AuthorService {

	Author addAuthor(String name);

	boolean existByAuthorName(String name);

	boolean existByAuthorId(int id);

	void updateAuthor(AuthorForm authorForm);

	List<AuthorResponse> fetchAllAuthors();

	Optional<Author> fetchAuthorByName(String name);

	void deleteAuthor(int id);

}
