package org.ecplise.shoply.service;

import java.util.List;
import java.util.Optional;

import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.model.Review;
import org.eclipse.shoply.web.dto.ReviewDto;

public interface ReviewService {

    Optional<Review> findOne(Long id);

    List<Review> findAll();

    Review save(Review review);

    List<Post> deletePost(Long id);
    
    Review delete(Long id);

	List<Review> deleteByPost(Long id);



}
