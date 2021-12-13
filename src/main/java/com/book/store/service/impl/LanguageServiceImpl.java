
package com.book.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.book.store.bean.LanguageForm;
import com.book.store.dto.LanguageResponse;
import com.book.store.exception.ValidationException;
import com.book.store.model.Language;
import com.book.store.repository.LanguageRepository;
import com.book.store.service.LanguageService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	private LanguageRepository languageRepository;

	@Override
	@Transactional
	public void addLanguage(String name) {

		try {
			Language language = new Language();
			language.setName(name);
			languageRepository.save(language);

		} catch (Exception e) {
			log.debug("LanguageServiceImpl : addLanguage - Exception {} ", e);
			throw new ValidationException("Invalid request");
		}

	}

	@Override
	public boolean existsByName(String name) {

		return languageRepository.existsByName(name);

	}

	@Override
	public void updateLanguage(LanguageForm languageForm) {
		Language language = new Language();
		language.setName(languageForm.getName());
		language.setId(languageForm.getId());
		languageRepository.save(language);

	}

	@Override
	public boolean existsByLanguageId(int id) {
		return languageRepository.existsById(id);
	}

	@Override
	public void deleteLanguage(int id) {
		try {
			languageRepository.deleteById(id);
		} catch (Exception ex) {
			throw new ValidationException("Language mapped with book, delete operation is not possible !");
		}
	}

	@Override
	public List<LanguageResponse> getAllLangauges() {
		List<Language> languageList = languageRepository.findAll();
		List<LanguageResponse> languageResponseList = new ArrayList<>();
		if (!ObjectUtils.isEmpty(languageList)) {
			languageList.forEach(language -> {
				languageResponseList
						.add(LanguageResponse.builder().id(language.getId()).name(language.getName()).build());
			});
		}
		return languageResponseList;
	}

	@Override
	public Optional<Language> fetchLanguageByName(String name) {
		return languageRepository.findByName(name);
	}

}
