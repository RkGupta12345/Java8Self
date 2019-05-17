package com.exilant.mongotemplate.dao;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.stereotype.Repository;

import com.exilant.mongotemplate.model.Person;
@Repository
public class PersonDaoImpl implements PersonDao{
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public boolean savePerson(Person person) {
		try {
		mongoTemplate.save(person);
		return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public List<Person> findAllPersons(Person person) {
		return mongoTemplate.findAll(Person.class);
		
	}

	@Override
	public Person findOneByName(String name) {
		Query query=new Query(where("name").is(name));
		return mongoTemplate.findOne(query, Person.class);
	}

	@Override
	public List<Person> findByAgeRange(int lowerBound, int higherBound) {
		
		Query query=new Query(where("age").gte(lowerBound).lte(higherBound));
		
		return mongoTemplate.find(query,Person.class);
	}

	@Override
	public List<Person> findByfavoriteBooks(String favoriteBooks) {
		Query query=new Query(where("favoriteBooks").regex(favoriteBooks));
		return mongoTemplate.find(query, Person.class);
	}

	@Override
	public Person findByPersonId(String personId) {
		
		return mongoTemplate.findById(personId, Person.class);
	}



}
