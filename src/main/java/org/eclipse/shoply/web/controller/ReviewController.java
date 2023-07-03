package org.eclipse.shoply.web.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.model.Review;
import org.eclipse.shoply.model.User;
import org.eclipse.shoply.support.ReviewDtoToReview;
import org.eclipse.shoply.support.ReviewToReviewDto;
import org.eclipse.shoply.web.dto.ReviewDto;
import org.ecplise.shoply.service.PostService;
import org.ecplise.shoply.service.ReviewService;
import org.ecplise.shoply.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private ReviewToReviewDto toReviewDto;
	@Autowired
	private ReviewDtoToReview toReview;
	
	@Autowired
	private UserServices userService;
	
	
	@GetMapping("/post/{id}")
	public ResponseEntity<List<ReviewDto>> getByPost(@PathVariable Long id){	
		Post post = postService.findOne(id);
		if(post==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<ReviewDto> reviews = toReviewDto.convert(post.getReviews());
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ReviewDto> getOne(@PathVariable Long id){	
		Review review = reviewService.findOne(id).orElse(null);
		if(review!=null) {
			return new ResponseEntity<>(toReviewDto.convert(review), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		
	}
	
	
	
	@PreAuthorize("hasAnyRole('SELLER', 'BUYER')")
	@PostMapping("/create")
	public ResponseEntity<Map<String,String>> create(@RequestBody ReviewDto dto,UriComponentsBuilder uriBuilder){
		
		if(dto.getReview().isEmpty() || dto.getReview()==null  || 
			 dto.getRating()<0  || dto.getRating()>5) {
			return new ResponseEntity<>(Map.of("message", "Review not valid"), HttpStatus.BAD_REQUEST);
		}
		
		Review review = toReview.convert(dto);

		if(dto.getPostId()!=null && dto.getUsername()!=null) {
			User user = userService.findByUsername(dto.getUsername());
			Post post = postService.findOne(dto.getPostId());
		
			review.setPost(post);
			review.setUser(user);
			reviewService.save(review); 
			
			  String createdResourceUri = uriBuilder.path("/api/reviews/{id}")
		                .buildAndExpand(review.getId())
		                .toUriString();
			   Map<String, String> responseBody = Map.of(
		                "uri", createdResourceUri,
		                "message", "Review saved successfully"
		        );

		
			 return ResponseEntity.created(URI.create(createdResourceUri)).body(responseBody);
		} else {
			return new ResponseEntity<>(Map.of("message", "Something went wrong"), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
		@PreAuthorize("hasAnyRole('SELLER', 'BUYER')")
		@Transactional
		@DeleteMapping("/{id}")
		public ResponseEntity<Map<String,String>> delete(@PathVariable Long id){
			Map<String,String> result = new HashMap<>();
			try {
				reviewService.delete(id);
				result.put("message","Review deleted sucessfully");
			} catch(NoSuchElementException ex){
				result.put("message","Review doesn't exist");
			}

		      return new ResponseEntity<>(result,HttpStatus.OK);
	}
}
