
package com.book.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.book.store.model.Preference;

@Repository
public interface PreferenceRepository extends CrudRepository<Preference, Integer> {

	@Query(value = "select  from preferences order by id desc", nativeQuery = true)
	List<Preference> findAll();
}
