
package com.book.store.service;

import java.util.List;
import java.util.Optional;

import com.book.store.bean.LanguageForm;
import com.book.store.model.Language;
import com.book.store.response.LanguageResponse;

public interface LanguageService {

	void addLanguage(String languageName);

	void updateLanguage(LanguageForm languageForm);

	boolean existsByName(String name);

	boolean existsByLanguageId(int id);

	void deleteLanguage(int id);

	List<LanguageResponse> getAllLangauges();
	
	Optional<Language> fetchLanguageByName(String name);

}
