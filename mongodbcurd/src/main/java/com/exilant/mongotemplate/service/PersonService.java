package com.exilant.mongotemplate.service;

import java.util.List;

import com.exilant.mongotemplate.model.Person;

public interface PersonService {
	boolean savePerson(Person person);
	List<Person> findAllPersons();
	Person findOneByName(String name);
	Person findByPersonId(String personId);
	List<Person> findByAgeRange(int lowerBound, int higherBound);
	List<Person> findByfavoriteBooks(String favoriteBooks);
	

}