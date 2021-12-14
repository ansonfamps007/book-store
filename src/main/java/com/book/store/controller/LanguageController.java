
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

import com.book.store.bean.LanguageForm;
import com.book.store.dto.LanguageDto;
import com.book.store.service.LanguageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/language")
@Slf4j
public class LanguageController {

	@Autowired
	private LanguageService languageService;

	/**
	 * 
	 * 
	 * @param languageForm
	 * @return
	 */
	@PostMapping(value = "/add", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Inserting Language")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public ResponseEntity<String> addLanguage(@Valid @RequestBody LanguageForm languageForm) {
		log.debug("LanguageController : addLanguage {} ");
		languageService.addLanguage(languageForm.getName());
		return ResponseEntity.ok("Successfully Inserted !");
	}

	@GetMapping(value = "/getAll", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Fetch All Languages")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"), })
	public ResponseEntity<List<LanguageDto>> getAllLanguages() {
		log.info("LanguageController - getAllLanguages {} ");
		return ResponseEntity.ok(languageService.getAllLanguages());
	}

	@GetMapping(value = "/get/{name}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Fetch Language By Name")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"), })
	public ResponseEntity<LanguageDto> getLanguageByName(@PathVariable String name) {
		log.info("LanguageController - getLanguageByName {} ", name);
		return ResponseEntity.ok(languageService.getLanguageByName(name));
	}

	@PatchMapping(value = "/update", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Updating Language")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public ResponseEntity<String> updateLanguage(@Valid @RequestBody LanguageForm languageForm) {
		log.debug("LanguageController : updateLanguage {} ");
		languageService.updateLanguage(languageForm);
		return ResponseEntity.ok("Successfully Updated !");
	}

	@DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Deleting Language")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public ResponseEntity<String> deleteLanguage(@PathVariable int id) {
		log.debug("LanguageController : deleteLanguage {} ");
		languageService.deleteLanguage(id);
		return ResponseEntity.ok("Successfully Deleted !");
	}

}
