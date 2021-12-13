
package com.book.store.service;

import java.util.List;
import java.util.Optional;

import com.book.store.bean.CategoryForm;
import com.book.store.dto.CategoryResponse;
import com.book.store.model.Category;

public interface CategoryService {

	boolean existsByName(String name);

	void addCategory(String name);

	boolean existsByCategoryId(int id);

	void updateCategory(CategoryForm categoryForm);

	void deleteCategory(int id);

	List<CategoryResponse> getAllCategories();

	Optional<Category> fetchCategoryByName(String name);

}
