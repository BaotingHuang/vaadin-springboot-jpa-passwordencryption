package com.zolee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zolee.domain.Person;
import com.zolee.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	public List<Person> findAll(){
		return personRepository.findAll();
	}
	
	public void save(Person person) {
		personRepository.save(person);
	}
	
	public Person findByName(String name) {
		return personRepository.findByName(name);
	} 
}
