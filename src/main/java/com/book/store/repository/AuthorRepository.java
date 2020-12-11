
package com.book.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.book.store.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Integer>{

	boolean existsByName(String name);
	
	List<Author> findAll();

	Optional<Author> findByName(String name);

}
