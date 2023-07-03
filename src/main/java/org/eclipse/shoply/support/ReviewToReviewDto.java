package org.eclipse.shoply.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.shoply.model.Review;
import org.eclipse.shoply.web.dto.ReviewDto;
import org.ecplise.shoply.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ReviewToReviewDto implements Converter<Review, ReviewDto> {

	@Override
	public ReviewDto convert(Review review) {
		ReviewDto dto = new ReviewDto();
		dto.setUsername(review.getUser().getUsername());;
		dto.setNameAndSurname(review.getUser().getName() + " " + review.getUser().getSurname());
		dto.setId(review.getId());
		dto.setRating(review.getRating());
		dto.setReview(review.getReview());
		return dto;
		
	}
	
	public List<ReviewDto> convert (List<Review> reviews){
		List<ReviewDto> result = new ArrayList<ReviewDto>();
		for (Review review : reviews) { 
			result.add(convert(review));
		}
		return result;
	}



}
