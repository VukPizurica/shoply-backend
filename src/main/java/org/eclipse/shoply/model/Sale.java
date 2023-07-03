package org.eclipse.shoply.model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	

    @ManyToMany(mappedBy = "sales")
    private List<Post> posts;
	

	@ManyToOne
	private User user;
	
	@Column
	private LocalDateTime dateAndTime;


	public Sale() {
		super();
	}



	public Sale(Long id, User user, LocalDateTime dateAndTime) {
		super();
		this.id = id;
		this.user = user;
		this.dateAndTime = dateAndTime;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}




	public List<Post> getPosts() {
		return posts;
	}



	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}




	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}




	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}








	@Override
	public int hashCode() {
		return Objects.hash(dateAndTime, id, posts, user);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(dateAndTime, other.dateAndTime) && Objects.equals(id, other.id)
				&& Objects.equals(posts, other.posts) && Objects.equals(user, other.user);
	}
	

	
}
