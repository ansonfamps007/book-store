
package com.book.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.store.bean.AuthorForm;
import com.book.store.dto.AuthorDto;
import com.book.store.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/author")
@Slf4j
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@PostMapping(value = "/add", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Inserting Author")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public ResponseEntity<String> addAuthor(@Valid @RequestBody AuthorForm authorForm) {
		log.debug("AuthorController : addAuthor {} ");
		authorService.addAuthor(authorForm.getName());
		return ResponseEntity.ok("Successfully Inserted !");
	}

	@GetMapping(value = "/getAll", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Fetch All Authors")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"), })
	public ResponseEntity<List<AuthorDto>> getAllAuthors() {
		log.info("AuthorController - getAllAuthors {} ");
		return ResponseEntity.ok(authorService.getAllAuthors());
	}

	@GetMapping(value = "/get/{name}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Fetch Author By Name")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"), })
	public ResponseEntity<AuthorDto> getAuthorByName(@PathVariable String name) {
		log.info("AuthorController - getAuthorByName {} ", name);
		return ResponseEntity.ok(authorService.getAuthorByName(name));
	}

	@PatchMapping(value = "/update", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Updating Author")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public ResponseEntity<String> updateAuthor(@Valid @RequestBody AuthorForm authorForm) {
		log.debug("AuthorController : updateAuthor {} ");
		authorService.updateAuthor(authorForm);
		return ResponseEntity.ok("Successfully Updated !");
	}

	@DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Deleting Author")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public ResponseEntity<String> deleteAuthor(@PathVariable int id) {
		log.debug("AuthorController : deleteAuthor {} ");
		authorService.deleteAuthor(id);
		return ResponseEntity.ok("Successfully Deleted !");
	}

}
