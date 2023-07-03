package org.eclipse.shoply.web.dto;

public class UserRegisterDto {

	private String name;
	
	private String surname;
	
	private int age;
	
	private String email;
	
	private String gender;
	
	private String password;
	
	private String confirmPassword;
	
	private String username;

	
	
	public UserRegisterDto() {
		super();
	}

	public UserRegisterDto(String name, String surname, int age, String email, String gender, String password,
			String confirmPassword, String username) {
		super();
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.email = email;
		this.gender = gender;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
