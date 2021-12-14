
package com.book.store.service;

import java.util.List;

import com.book.store.bean.LanguageForm;
import com.book.store.dto.LanguageDto;

public interface LanguageService {

	void addLanguage(String name);

	void updateLanguage(LanguageForm languageForm);

	LanguageDto getLanguageByName(String name);

	void deleteLanguage(int id);

	List<LanguageDto> getAllLanguages();

}
