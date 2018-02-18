package com.zolee.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zolee.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

	List<Person> findAll();
	
	Person findByName(String name);
	
}
