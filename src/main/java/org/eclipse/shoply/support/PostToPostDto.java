package org.eclipse.shoply.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.model.Review;
import org.eclipse.shoply.web.dto.PostDto;
import org.eclipse.shoply.web.dto.PostDtoSeller;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostToPostDto implements Converter<Post, PostDto> {

	@Override 
	public PostDto convert(Post post) { 
		PostDto dto = new PostDto();
		dto.setDescription(post.getDescription());
		dto.setId(post.getId()); 
		dto.setPrice(post.getPrice());
		dto.setTitle(post.getTitle());
		dto.setImage(post.getImage());
		dto.setViews(post.getViews());
		dto.setDate(post.getDatePosted());
		dto.setCategory(post.getCategory());
		dto.setUserId(post.getUser().getId());
		dto.setUsername(post.getUser().getName() + " " + post.getUser().getSurname());
		List<Long> reviewIds = new ArrayList<Long>();
		for (Review review: post.getReviews()) {
			reviewIds.add(review.getId());
		}
		dto.setReviews(reviewIds);
	
		return dto;
	}  
	
	public List<PostDto> convert(List<Post> posts) {
	List<PostDto> result = new ArrayList<PostDto>();
	for (Post post: posts) {
		result.add(convert(post));
	}
		
		return result;
	}

	public PostDtoSeller convertForSeller(Post post) {
		PostDtoSeller dto = new PostDtoSeller();
		dto.setTitle(post.getTitle());
		dto.setId(post.getId());
		dto.setCategory(post.getCategory());
		dto.setImage(post.getImage());
		dto.setPrice(post.getPrice());
		dto.setSales(post.getSales().size());
		dto.setViews(post.getViews());
		return dto;
	}
	
	public List<PostDtoSeller> convertForSeller(List<Post> posts) {
	List<PostDtoSeller> result = new ArrayList<PostDtoSeller>();
	for (Post post: posts) {
		result.add(convertForSeller(post));
	}
		return result;
	}
}
