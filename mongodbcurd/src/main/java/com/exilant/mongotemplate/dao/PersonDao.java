package com.exilant.mongotemplate.dao;

import java.util.List;

import com.exilant.mongotemplate.model.Person;

public interface PersonDao {
	boolean savePerson(Person person);
	List<Person> findAllPersons(Person person);
	Person findOneByName(String name);
	Person findByPersonId(String personId);
	List<Person> findByAgeRange(int lowerBound, int higherBound);
	List<Person> findByfavoriteBooks(String favoriteBooks);
	
}
