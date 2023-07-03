package org.eclipse.shoply.web.dto;

import java.util.Objects;

public class ReviewDto {

    private Long id;
    private String review;
    private double rating;
    private Long userId;
    private String nameAndSurname;
    private Long postId;
    private String username;

    public ReviewDto() {
        super();
    }

    public ReviewDto(Long id, String review, double rating, Long userId, String nameAndSurname, Long postId, String username) {
        super();
        this.id = id;
        this.review = review;
        this.rating = rating;
        this.userId = userId;
        this.nameAndSurname = nameAndSurname;
        this.postId = postId;
        this.username = username;
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(String nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, review);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReviewDto other = (ReviewDto) obj;
        return Objects.equals(id, other.id) && Objects.equals(review, other.review) && rating == other.rating;
    }
}
