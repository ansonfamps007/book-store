
package com.book.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.store.bean.BookForm;
import com.book.store.dto.BookDto;
import com.book.store.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/book")
@Slf4j
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping(value = "/add", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Inserting Book")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public ResponseEntity<String> addBook(@Valid @RequestBody BookForm bookForm) {
		log.debug("BookController : addBook {} ");
		bookService.addBook(bookForm);
		return ResponseEntity.ok("Successfully Inserted !");
	}

	@GetMapping(value = "/getAll", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Fetch All Books")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"), })
	public ResponseEntity<List<BookDto>> getAllBooks(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageLimit") int pageLimit) {
		log.info("BookController - getAllBooks {} ");
		return ResponseEntity.ok(bookService.getAllBooks(pageNo, pageLimit));
	}

	@GetMapping(value = "/get/{name}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Fetch All Books")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"), })
	public ResponseEntity<List<BookDto>> getBookByName(@PathVariable String name) {
		log.info("BookController - getAllBooks {} ");
		return ResponseEntity.ok(bookService.getBookByName(name));
	}

	/*
	 * 
	 * @PatchMapping(value = "/update", produces = {
	 * MediaType.APPLICATION_JSON_VALUE }, consumes = {
	 * MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @Operation(summary = "Updating Book")
	 * 
	 * @ApiResponses(value = { @ApiResponse(responseCode = "200", description =
	 * "Success"),
	 * 
	 * @ApiResponse(responseCode = "400", description = "Invalid Data"),
	 * 
	 * @ApiResponse(responseCode = "406", description = "Validation exception") })
	 * public ResponseEntity<String> updateBook(@Valid @RequestBody BookForm
	 * bookForm) { log.debug("BookController : updateBook {} ");
	 * bookService.updateBook(bookForm); return
	 * ResponseEntity.ok("Successfully Updated !"); }
	 * 
	 * @DeleteMapping(value = "/delete/{id}", produces = {
	 * MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @Operation(summary = "Deleting Book")
	 * 
	 * @ApiResponses(value = { @ApiResponse(responseCode = "200", description =
	 * "Success"),
	 * 
	 * @ApiResponse(responseCode = "400", description = "Invalid Data"),
	 * 
	 * @ApiResponse(responseCode = "406", description = "Validation exception") })
	 * public ResponseEntity<String> deleteBook(@PathVariable int id) {
	 * log.debug("BookController : deleteBook {} "); bookService.deleteBook(id);
	 * return ResponseEntity.ok("Successfully Deleted !"); }
	 */

}
