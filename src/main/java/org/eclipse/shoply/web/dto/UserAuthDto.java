package org.eclipse.shoply.web.dto;

public class UserAuthDto {

	
	private String username;
	
	private String password;

	
	
	public UserAuthDto() {
		super();
	}

	public UserAuthDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
