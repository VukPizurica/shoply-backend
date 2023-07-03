package org.eclipse.shoply.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.model.Review;
import org.eclipse.shoply.repository.ReviewRepository;
import org.ecplise.shoply.service.PostService;
import org.ecplise.shoply.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaReviewService implements ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	

	public Optional<Review> findOne(Long id) {
		return reviewRepository.findById(id);
	}

	public List<Review> findAll() {
		
		return reviewRepository.findAll();
	}

	public Review save(Review review) {
		return reviewRepository.save(review);
	}

	public List<Review> deleteByPost(Long id) {
		return reviewRepository.deleteByPostId(id);
	}

	@Override
	public Review delete(Long id) {
	    Optional<Review> optionalReview = reviewRepository.findById(id);
	    if (optionalReview.isPresent()) {
	        Review review = optionalReview.get();
	        reviewRepository.deleteById(id);
	        return review;
	    } else {
	        throw new NoSuchElementException("No Review found with ID " + id);
	    }
	}


	@Override
	public List<Post> deletePost(Long id) {
		// TODO Auto-generated method stub
		return null;
	}




}
