package org.eclipse.shoply.web.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class SaleDto {

	
	private Long id;
	
	private LocalDateTime dateAndTime;
	
	private Map<Long,Double> amounts;
	
	private String userGender;

	private String username;

	public SaleDto() {
		super();
	}
	
	
	public SaleDto(Long id, LocalDateTime dateAndTime) {
		super();
		this.id = id;
		this.dateAndTime = dateAndTime;
	}




	public SaleDto(Long id, LocalDateTime dateAndTime, Map<Long, Double> amounts, String userGender, String username) {
		super();
		this.id = id;
		this.dateAndTime = dateAndTime;
		this.amounts = amounts;
		this.userGender = userGender;
		this.username = username;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public Map<Long, Double> getAmounts() {
		return amounts;
	}


	public void setAmounts(Map<Long, Double> amounts) {
		this.amounts = amounts;
	}


	public String getUserGender() {
		return userGender;
	}


	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}


	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	
	
	
	
}
