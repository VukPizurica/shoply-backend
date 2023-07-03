package org.eclipse.shoply.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String review;
	
	@Column
	private double rating;

	@ManyToOne
	private Post post;
	
	@ManyToOne
	private User user;


	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Review() {
		super();
	}
	
	public String getReview() {
		return review;
	}
	
	public Review(String review, double rating, Post post, User user) {
		super();
		this.review = review;
		this.rating = rating;
		this.post = post;
		this.user = user;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double d) {
		this.rating = d;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(id, post, rating, review, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		return Objects.equals(id, other.id) && Objects.equals(post, other.post)
				&& Double.doubleToLongBits(rating) == Double.doubleToLongBits(other.rating)
				&& Objects.equals(review, other.review) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Review [review=" + review + ", rating=" + rating + ", post=" + post + ", user=" + user + "]";
	}
	
	
	
}
