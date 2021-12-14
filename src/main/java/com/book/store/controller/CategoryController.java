
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

import com.book.store.bean.CategoryForm;
import com.book.store.dto.CategoryDto;
import com.book.store.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 
	 * 
	 * @param categoryForm
	 * @return
	 */
	@PostMapping(value = "/add", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Inserting Category")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryForm categoryForm) {
		log.debug("CategoryController : addCategory {} ");
		categoryService.addCategory(categoryForm.getName());
		return ResponseEntity.ok("Successfully Inserted !");
	}

	/**
	 * 
	 * @param categoryForm
	 * @return
	 */
	@GetMapping(value = "/getAll", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Fetch All Categories")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"), })
	public ResponseEntity<List<CategoryDto>> getAllCategorys() {
		log.info("CategoryController - getAllCategories {} ");
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	@GetMapping(value = "/get/{name}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Fetch Category By Name")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"), })
	public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable String name) {
		log.info("CategoryController - getCategoryByName {} ", name);
		return ResponseEntity.ok(categoryService.getCategoryByName(name));
	}

	@PatchMapping(value = "/update", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Updating Category")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public ResponseEntity<String> updateCategory(@Valid @RequestBody CategoryForm categoryForm) {
		log.debug("CategoryController : updateCategory {} ");
		categoryService.updateCategory(categoryForm);
		return ResponseEntity.ok("Successfully Updated !");
	}

	/**
	 * 
	 * @param categoryForm
	 * @return
	 */
	@DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Deleting Category")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Invalid Data"),
			@ApiResponse(responseCode = "406", description = "Validation exception") })
	public ResponseEntity<String> deleteCategory(@PathVariable int id) {
		log.debug("CategoryController : deleteCategory {} ");
		categoryService.deleteCategory(id);
		return ResponseEntity.ok("Successfully Deleted !");
	}
}
