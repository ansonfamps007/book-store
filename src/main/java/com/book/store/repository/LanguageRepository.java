
package com.book.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.book.store.model.Language;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Integer> {

	boolean existsByName(String name);

	List<Language> findAll();

	Optional<Language> findByName(String name);

}
