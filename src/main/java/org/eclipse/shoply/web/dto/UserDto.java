package org.eclipse.shoply.web.dto;

import javax.persistence.Enumerated;

import org.eclipse.shoply.enumeration.UserRole;

public class UserDto {

	
	private Long id;
	
	private String gender;
	
	private int age;
	
	private String country;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String username;
	
	private String password;
	
	private String role;

	public UserDto() {
		super();
	}

	public Long getId() {
		return id;
	}
	
	public UserDto(Long id, String gender, int age, String country) {
		super();
		this.id = id;
		this.gender = gender;
		this.age = age;
		this.country = country;
	}


	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setRole(String role2) {
		this.role=role2;
		
	}

	public String getRole() {
		return role;
	}
	
	
}
