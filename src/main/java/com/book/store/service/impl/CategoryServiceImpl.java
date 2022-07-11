
package com.book.store.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.book.store.bean.CategoryForm;
import com.book.store.dto.CategoryDto;
import com.book.store.exception.ValidationException;
import com.book.store.model.Category;
import com.book.store.repository.CategoryRepository;
import com.book.store.service.CategoryService;
import com.book.store.util.ApiConstants;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addCategory(String name) {
		if (!categoryRepository.existsByName(name)) {
			Category category = new Category();
			category.setName(name);
			categoryRepository.save(category);
		} else {
			throw new ValidationException("Category name already exist !");
		}
	}

	@Override
	public void updateCategory(CategoryForm categoryForm) {

		int categoryId = categoryForm.getId();
		if (categoryRepository.existsById(categoryId)) {
			Category category = categoryRepository.findById(categoryId).get();
			category.setName(categoryForm.getName());
			categoryRepository.save(category);
		} else {
			throw new ValidationException("No Category found with given category ID: " + categoryId);
		}
	}

	@Override
	public void deleteCategory(int categoryId) {
		if (categoryRepository.existsById(categoryId)) {
			try {
				categoryRepository.deleteById(categoryId);
			} catch (Exception ex) {
				throw new ValidationException("Category mapped with book, delete operation is not possible !");
			}
		} else {
			throw new ValidationException("No Category found with given category ID: " + categoryId);
		}
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categoryList = categoryRepository.findAll();
		if (!ObjectUtils.isEmpty(categoryList)) {
			return categoryList.stream()
					.map(category -> CategoryDto.builder().id(category.getId()).name(category.getName()).build())
					.collect(Collectors.toList());
		} else {
			throw new ValidationException("No Categorys found!");
		}
	}

	@Override
	public CategoryDto getCategoryByName(String name) {
		return categoryRepository.findByName(name)
				.map(category -> CategoryDto.builder().id(category.getId()).name(
						category.getName()).build())
				.orElseThrow(() -> new ValidationException(ApiConstants.NO_DATA));
	}

}
