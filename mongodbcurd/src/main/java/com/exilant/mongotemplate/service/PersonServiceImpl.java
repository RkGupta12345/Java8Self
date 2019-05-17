package com.exilant.mongotemplate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exilant.mongotemplate.dao.PersonDaoImpl;
import com.exilant.mongotemplate.model.Person;
@Service
public class PersonServiceImpl implements PersonService{
	@Autowired
	PersonDaoImpl personDaoImpl;

	@Override
	public boolean savePerson(Person person) {
		
		return personDaoImpl.savePerson(person);
	}


	@Override
	public List<Person> findAllPersons() {
		return personDaoImpl.findAllPersons(null);
	}


	@Override
	public Person findOneByName(String name) {
		Person person=personDaoImpl.findOneByName(name);
		return person;
	}


	@Override
	public List<Person> findByAgeRange(int lowerBound, int higherBound) {
		
		return personDaoImpl.findByAgeRange(lowerBound, higherBound);
	}


	@Override
	public List<Person> findByfavoriteBooks(String favoriteBooks) {
		
		return personDaoImpl.findByfavoriteBooks(favoriteBooks);
	}


	@Override
	public Person findByPersonId(String personId) {
		
		return personDaoImpl.findByPersonId(personId);
	}

	

}
