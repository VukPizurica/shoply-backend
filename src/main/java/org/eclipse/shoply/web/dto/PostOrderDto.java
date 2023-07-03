package org.eclipse.shoply.web.dto;

import java.util.Map;

public class PostOrderDto {

	//Map of post Id and it's amount ordered
	Map<Long,Integer> posts;
	
	private String username;

	
	public PostOrderDto() {
		super();
	}


	public PostOrderDto(Map<Long, Integer> posts, String username) {
		super();
		this.posts = posts;
		this.username = username;
	}


	public Map<Long, Integer> getPosts() {
		return posts;
	}


	public void setPosts(Map<Long, Integer> posts) {
		this.posts = posts;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	
	
	
	
}
