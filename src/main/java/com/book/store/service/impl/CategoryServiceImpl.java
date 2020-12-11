
package com.book.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.book.store.bean.CategoryForm;
import com.book.store.exception.ValidationException;
import com.book.store.model.Category;
import com.book.store.repository.CategoryRepository;
import com.book.store.response.CategoryResponse;
import com.book.store.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public boolean existsByName(String name) {
		return categoryRepository.existsByName(name);
	}

	@Override
	public void addCategory(String name) {
		try {
			Category category = new Category();
			category.setName(name);
			categoryRepository.save(category);
		} catch (Exception e) {

			log.debug("CategoryServiceImpl : addCategory - Exception {} ", e);
			throw new ValidationException("Invalid request");
		}

	}

	@Override
	public boolean existsByCategoryId(int id) {
		return categoryRepository.existsById(id);
	}

	@Override
	public void updateCategory(CategoryForm categoryForm) {

		Category category = new Category();
		category.setName(categoryForm.getName());
		category.setId(categoryForm.getId());
		categoryRepository.save(category);

	}

	@Override
	public void deleteCategory(int id) {
		try {
			categoryRepository.deleteById(id);
		} catch (Exception ex) {
			throw new ValidationException("Category mapped with book, delete operation is not possible !");
		}

	}

	@Override
	public List<CategoryResponse> getAllCategories() {
		List<Category> categoryList = categoryRepository.findAll();
		List<CategoryResponse> categoryResponseList = new ArrayList<>();
		if (!ObjectUtils.isEmpty(categoryList)) {
			categoryList.forEach(category -> {
				categoryResponseList
						.add(CategoryResponse.builder().id(category.getId()).name(category.getName()).build());
			});
		}
		return categoryResponseList;
	}

	@Override
	public Optional<Category> fetchCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}

}
