
package com.book.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.store.model.Preference;
import com.book.store.repository.PreferenceRepository;
import com.book.store.service.PreferenceService;

@Service
public class PreferenceServiceImpl implements PreferenceService {
	
	@Autowired
	private PreferenceRepository preferenceRepository;

	@Override
	public List<Preference> getAllPreferences() {
		try {
			
			return preferenceRepository.findAll();
			
		} catch (Exception e) {
			

		}
		return null;
	}

}
