package org.eclipse.shoply.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.web.dto.PostDto;
import org.eclipse.shoply.web.dto.PostDtoSeller;
import org.ecplise.shoply.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostDtoToPost implements Converter<PostDto, Post>{

	
	@Autowired
	private PostService postService;
	

	public Post convert(PostDto dto) {
		Long id = dto.getId();
		Post post = id == null ? new Post() : postService.findOne(id);

		if(post!=null) {
			if(dto.getId()!=null) {
				post.setId(id);
			}
			if(dto.getCategory()!=null && !dto.getCategory().name().isEmpty()) {
				post.setCategory(dto.getCategory());
			}
		
			if(dto.getImage()!=null && !dto.getImage().isEmpty()) {
				post.setImage(dto.getImage());
			}

			if(dto.getViews()>0 ){
				post.setViews(dto.getViews());
			}
		
			post.setPrice(dto.getPrice());
			post.setTitle(dto.getTitle());
			post.setDescription(dto.getDescription());
			
		} 

		return post;
		
	
	}
	
	public Post convert(PostDtoSeller dto) {
		Long id = dto.getId();
		Post post = id == null ? new Post() : postService.findOne(id);
		
		if(post!=null) {
			post.setId(id);
			post.setPrice(dto.getPrice());
			post.setTitle(dto.getTitle());
			post.setDescription(dto.getDescription());
			post.setImage(dto.getImage());
		}
		
		return post;
	}

	
	public List<Post> convert(List<PostDto> posts){
		List<Post> result = new ArrayList<Post>();
		
		for (PostDto postDto : posts) {
			result.add(convert(postDto));
		}
		return result;
	}
}
