package com.exilant.mongotemplate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exilant.mongotemplate.model.Person;
import com.exilant.mongotemplate.service.PersonService;

@RestController
public class PersonController {
	
	
	public PersonController() {
		System.out.println("Person controller called::");
	}

	@Autowired
	PersonService ps;
	
	@PostMapping("/savePerson")
	public String  savePerson(@RequestBody Person person) {
		String msg="";
		boolean b=ps.savePerson(person);
		if(b) {
			msg="Person:=>> "+person.getPersonId()+"saved in database";
		}
		else
		{
			msg="Person:=>> "+person.getPersonId()+"is not saved in database";
		}
		return msg;
	}
	
	@GetMapping("/getAllPersons")
	public List<Person> findAllPerson( )
	{
		return ps.findAllPersons();
		
	}
	@GetMapping("/findOneByName/{name}")
	public Person findOneByName(@PathVariable String name)
	{
		return ps.findOneByName(name);
		
	}
	
	@GetMapping("/findByAgeRange/{lowerBound}/{higherBound}")
	public List<Person> findByAgeRange(@PathVariable int lowerBound,@PathVariable int higherBound){
		return ps.findByAgeRange(lowerBound, higherBound);
		
	}
	@GetMapping("/findByfavoriteBooks/{favoriteBooks}")
	public List<Person> findByfavoriteBooks(@PathVariable String favoriteBooks){
		return ps.findByfavoriteBooks(favoriteBooks);
		
	}
	
	@GetMapping("/findByPersonId/{personId}")
	public Person findByPersonId(@PathVariable String personId) {
		Person person1=ps.findByPersonId(personId);
		System.out.println("Person Found In Database::=>>"+personId);
		return person1;
	
	}
}
