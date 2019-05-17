package com.exilant.mongotemplate.mongodbcurd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@SpringBootApplication
@ComponentScan(basePackages="com.exilant.mongotemplate.*")
@Configuration

public class MongodbcurdApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongodbcurdApplication.class, args);
	}
	
	@Bean
	public MongoTemplate mongoTemplate()
	{
		MongoClient mongoClient=new MongoClient("localhost",27019);
		return new MongoTemplate(mongoClient, "exdb");
	}
}
