package com.exilant.mongotemplate.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
@Document(collection = "person")
public class Person {
	@Id
	private String personId;
	private String name;
	private int age;
	private String sex;
	private String city;
	private String state;
	private String country;

	private List<String> favoriteBooks;
	private String dateOfBirth;

	public Person() {
		super();
	}

	public Person(String personId, String name, int age, String sex, String city, String state, String country,
			List<String> favoriteBooks, String dateOfBirth) {
		this.personId = personId;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.city = city;
		this.state = state;
		this.country = country;
		this.favoriteBooks = favoriteBooks;
		this.dateOfBirth = dateOfBirth;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<String> getFavoriteBooks() {
		return favoriteBooks;
	}

	public void setFavoriteBooks(List<String> favoriteBooks) {
		this.favoriteBooks = favoriteBooks;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", name=" + name + ", age=" + age + ", sex=" + sex + ", city=" + city
				+ ", state=" + state + ", country=" + country + ", favoriteBooks=" + favoriteBooks + ", dateOfBirth="
				+ dateOfBirth + "]";
	}

}