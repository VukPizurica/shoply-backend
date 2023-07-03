package org.eclipse.shoply.web.dto;

import org.eclipse.shoply.enumeration.PostCategory;

public class PostDtoSeller {
	
	private Long id;
	
	private String title;
	
	private double price;
	
	private String image;

	private PostCategory category;
	
	private String description;

	private int sales;

	private int views;
	
	public PostDtoSeller() {
		super();
	}

	public PostDtoSeller(Long id, String title, double price, String description) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
	}
	

	
	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PostCategory getCategory() {
		return category;
	}

	public void setCategory(PostCategory category) {
		this.category = category;
	}



	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}




	
}
