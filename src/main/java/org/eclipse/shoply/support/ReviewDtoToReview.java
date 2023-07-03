package org.eclipse.shoply.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.model.Review;
import org.eclipse.shoply.model.User;
import org.eclipse.shoply.web.dto.ReviewDto;
import org.ecplise.shoply.service.PostService;
import org.ecplise.shoply.service.ReviewService;
import org.ecplise.shoply.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ReviewDtoToReview implements Converter<ReviewDto, Review> {

	
	@Autowired
	private ReviewService reviewService;
	
	
	@Override
	public Review convert(ReviewDto dto) {
		Long id = dto.getId();
		
		Review review = id ==null ? new Review() : reviewService.findOne(id).get();

		review.setRating(dto.getRating());
		review.setReview(dto.getReview());
		
		return review;
	}
	
	
	public List<Review> convert (List<ReviewDto> reviews){
		List<Review> result = new ArrayList<Review>();
		for (ReviewDto review : reviews) {
			result.add(convert(review));
		}
		return result;
	};

}
