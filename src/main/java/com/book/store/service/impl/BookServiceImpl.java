
package com.book.store.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.book.store.bean.BookForm;
import com.book.store.dto.BookDto;
import com.book.store.exception.ValidationException;
import com.book.store.model.Book;
import com.book.store.repository.AuthorRepository;
import com.book.store.repository.BookRepository;
import com.book.store.repository.CategoryRepository;
import com.book.store.repository.LanguageRepository;
import com.book.store.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	private final AuthorRepository authorRepository;

	private final CategoryRepository categoryRepository;

	private final LanguageRepository languageRepository;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
			CategoryRepository categoryRepository, LanguageRepository languageRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.categoryRepository = categoryRepository;
		this.languageRepository = languageRepository;

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addBook(BookForm bookForm) {
		if (!bookRepository.existsByTitle(bookForm.getTitle())) {
			Book book = Book.builder().title(bookForm.getTitle()).description(bookForm.getDescription())
					.author(authorRepository.findByName(bookForm.getAuthor()).orElseThrow(
							() -> new ValidationException("No Author found with given name: " + bookForm.getAuthor())))
					.category(categoryRepository.findByName(bookForm.getCategory())
							.orElseThrow(() -> new ValidationException(
									"No Category found with given name: " + bookForm.getCategory())))
					.language(languageRepository.findByName(bookForm.getLanguage())
							.orElseThrow(() -> new ValidationException(
									"No Language found with given name: " + bookForm.getLanguage())))
					.build();
			bookRepository.save(book);
		} else {
			throw new ValidationException("Book name already exist !");
		}
	}

	@Override
	public List<BookDto> getAllBooks() {
		List<Book> bookList = bookRepository.findAll();
		if (!ObjectUtils.isEmpty(bookList)) {
			return bookList.stream().map(mapBookResponse()).collect(Collectors.toList());
		} else {
			throw new ValidationException("No Books found!");
		}
	}

	@Override
	public BookDto getBookByName(String name) {
		return bookRepository.findByTitleContains(name).map(mapBookResponse())
				.orElseThrow(() -> new ValidationException("No Book found!"));
	}

	private Function<? super Book, ? extends BookDto> mapBookResponse() {
		return book -> BookDto.builder().id(book.getId()).title(book.getTitle()).description(book.getDescription())
				.author(book.getAuthor().getName()).category(book.getCategory().getName())
				.language(book.getLanguage().getName()).createdAt(book.getCreatedAt()).build();
	}

	/*
	 * @Override public void updateBook(BookForm bookForm) {
	 * 
	 * int bookId = bookForm.getId(); if (bookRepository.existsById(bookId)) { Book
	 * book = bookRepository.findById(bookId).get();
	 * book.setName(bookForm.getName()); bookRepository.save(book); } else { throw
	 * new ValidationException("No Book found with given book ID: " + bookId); } }
	 * 
	 * @Override public void deleteBook(int bookId) { if
	 * (bookRepository.existsById(bookId)) { try {
	 * bookRepository.deleteById(bookId); } catch (Exception ex) { throw new
	 * ValidationException("Book mapped with book, delete operation is not possible !"
	 * ); } } else { throw new
	 * ValidationException("No Book found with given book ID: " + bookId); } }
	 * 
	 * 
	 * 
	 * @Override public BookDto getBookByName(String name) { return
	 * bookRepository.findByName(name) .map(book ->
	 * BookDto.builder().id(book.getId()).name(book.getName()).build())
	 * .orElseThrow(() -> new ValidationException(ApiConstants.NO_DATA)); }
	 */
}
