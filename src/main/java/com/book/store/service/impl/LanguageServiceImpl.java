
package com.book.store.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.book.store.bean.LanguageForm;
import com.book.store.dto.LanguageDto;
import com.book.store.exception.ValidationException;
import com.book.store.model.Language;
import com.book.store.repository.LanguageRepository;
import com.book.store.service.LanguageService;
import com.book.store.util.ApiConstants;

@Service
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	private LanguageRepository languageRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addLanguage(String name) {
		if (!languageRepository.existsByName(name)) {
			Language language = new Language();
			language.setName(name);
			languageRepository.save(language);
		} else {
			throw new ValidationException("Language name already exist !");
		}
	}

	@Override
	public void updateLanguage(LanguageForm languageForm) {

		int languageId = languageForm.getId();
		if (languageRepository.existsById(languageId)) {
			Language language = languageRepository.findById(languageId).get();
			language.setName(languageForm.getName());
			languageRepository.save(language);
		} else {
			throw new ValidationException("No Language found with given language ID: " + languageId);
		}
	}

	@Override
	public void deleteLanguage(int languageId) {
		if (languageRepository.existsById(languageId)) {
			try {
				languageRepository.deleteById(languageId);
			} catch (Exception ex) {
				throw new ValidationException("Language mapped with book, delete operation is not possible !");
			}
		} else {
			throw new ValidationException("No Language found with given language ID: " + languageId);
		}
	}

	@Override
	public List<LanguageDto> getAllLanguages() {
		List<Language> languageList = languageRepository.findAll();
		if (!ObjectUtils.isEmpty(languageList)) {
			return languageList.stream()
					.map(language -> LanguageDto.builder().id(language.getId()).name(language.getName()).build())
					.collect(Collectors.toList());
		} else {
			throw new ValidationException("No Languages found!");
		}
	}

	@Override
	public LanguageDto getLanguageByName(String name) {
		return languageRepository.findByName(name)
				.map(language -> LanguageDto.builder().id(language.getId()).name(language.getName()).build())
				.orElseThrow(() -> new ValidationException(ApiConstants.NO_DATA));
	}

}
