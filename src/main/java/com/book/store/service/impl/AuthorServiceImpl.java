
package com.book.store.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.book.store.bean.AuthorForm;
import com.book.store.dto.AuthorDto;
import com.book.store.exception.ValidationException;
import com.book.store.model.Author;
import com.book.store.repository.AuthorRepository;
import com.book.store.service.AuthorService;
import com.book.store.util.ApiConstants;

@Service
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;

	@Autowired
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addAuthor(String name) {
		if (!authorRepository.existsByName(name)) {
			Author author = new Author();
			author.setName(name);
			authorRepository.save(author);
		} else {
			throw new ValidationException("Author name already exist !");
		}
	}

	@Override
	public void updateAuthor(AuthorForm authorForm) {

		int authorId = authorForm.getId();
		if (authorRepository.existsById(authorId)) {
			Author author = authorRepository.findById(authorId).get();
			author.setName(authorForm.getName());
			authorRepository.save(author);
		} else {
			throw new ValidationException("No Author found with given author ID: " + authorId);
		}
	}

	@Override
	public void deleteAuthor(int authorId) {
		if (authorRepository.existsById(authorId)) {
			try {
				authorRepository.deleteById(authorId);
			} catch (Exception ex) {
				throw new ValidationException("Author mapped with book, delete operation is not possible !");
			}
		} else {
			throw new ValidationException("No Author found with given author ID: " + authorId);
		}
	}

	@Override
	public List<AuthorDto> getAllAuthors() {
		List<Author> authorList = authorRepository.findAll();
		if (!ObjectUtils.isEmpty(authorList)) {
			return authorList.stream()
					.map(author -> AuthorDto.builder().id(author.getId()).name(author.getName()).build())
					.collect(Collectors.toList());
		} else {
			throw new ValidationException("No Authors found!");
		}
	}

	@Override
	public AuthorDto getAuthorByName(String name) {
		return authorRepository.findByName(name)
				.map(author -> AuthorDto.builder().id(author.getId()).name(author.getName()).build())
				.orElseThrow(() -> new ValidationException(ApiConstants.NO_DATA));
	}

	@Override
	public List<String> getBooks(String name) {
		return authorRepository.findByName(name).get().getBooks().stream().map(s -> s.getTitle())
				.collect(Collectors.toList());
	}
}
