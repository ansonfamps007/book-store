
package com.book.store.service;

import java.util.List;

import com.book.store.bean.CategoryForm;
import com.book.store.dto.CategoryDto;

public interface CategoryService {

	void addCategory(String name);

	void updateCategory(CategoryForm authorForm);

	CategoryDto getCategoryByName(String name);

	void deleteCategory(int id);

	List<CategoryDto> getAllCategories();

}
