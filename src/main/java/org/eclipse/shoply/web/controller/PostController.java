package org.eclipse.shoply.web.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.shoply.enumeration.PostCategory;
import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.model.User;
import org.eclipse.shoply.security.TokenUtils;
import org.eclipse.shoply.support.PostDtoToPost;
import org.eclipse.shoply.support.PostToPostDto;
import org.eclipse.shoply.web.dto.PostDto;
import org.eclipse.shoply.web.dto.PostDtoSeller;
import org.ecplise.shoply.service.PostService;
import org.ecplise.shoply.service.ReviewService;
import org.ecplise.shoply.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {



	@Autowired
	private PostService postService;
	
	@Autowired  
	private UserServices userService;
	@Autowired  
	private TokenUtils tokenUtils;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private PostToPostDto toPostDto;
	@Autowired
	private PostDtoToPost toPost;
	

	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<List<PostDto>> getAll(){
		List<Post> posts = postService.findAll();  
		return new ResponseEntity<>(toPostDto.convert(posts), HttpStatus.OK);
	};
	
	
	
	@PreAuthorize("permitAll()")
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getOne(@PathVariable Long id, @RequestParam(name = "increment", required = false) boolean increment, @RequestParam(name = "jwt", required = false) String jwt) {
	    Post post = postService.findOne(id);
	    if(jwt != null) {
	        String username = tokenUtils.getUsernameFromToken(jwt);
	        User user = userService.findByUsername(username);
	        if(user ==null) {
	        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        if(!user.getPosts().contains(post)){
	        	return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
	        }
	    }
	    if (post == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    if (increment) {
	        post.setViews(post.getViews() + 1);
	        postService.save(post);
	    }
	    return new ResponseEntity<>(toPostDto.convert(post), HttpStatus.OK);
	}



	@PreAuthorize("hasRole('SELLER')")	
	@GetMapping("/allCategories")
	public ResponseEntity<List<String>> getAllCategories(){	
		List<String> categories = new ArrayList<>();
		for(PostCategory category: PostCategory.values()) {
			categories.add(category.toString());
		}
		
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/categories")
	public ResponseEntity<List<String>> getActiveCategories(){
		List<String> categories = new ArrayList<>();
		List<Post> posts = postService.findAll();
		for (Post post: posts) {
			if(!categories.contains(post.getCategory().toString()) && !post.isDeleted()) {
				categories.add(post.getCategory().toString());
			}
	
		}
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}
	
	
	
	
	@GetMapping("/category/{category}")
	public ResponseEntity<Page<PostDto>> getByCategory(@PathVariable String category, @RequestParam(required = false, defaultValue = "0") int page) {
	    if(PostCategory.valueOf(category)==null) {
	    	  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		Page<Post> posts = postService.findByCategory(PostCategory.valueOf(category), page);
	    if(posts!=null) {
	    	   Page<PostDto> postDtos = posts.map(post -> {
	   	        PostDto postDto = new PostDto();
	   	        postDto.setId(post.getId());
	   	        postDto.setTitle(post.getTitle());
	   	        postDto.setDescription(post.getDescription());
	   			postDto.setId(post.getId()); 
	   			postDto.setPrice(post.getPrice());
	   			postDto.setTitle(post.getTitle());
	   			postDto.setImage(post.getImage());
	   			postDto.setViews(post.getViews());
	   			postDto.setDate(post.getDatePosted());
	   			postDto.setCategory(post.getCategory());
	   			postDto.setUserId(post.getUser().getId());
	   			postDto.setUsername(post.getUser().getName() + " " + post.getUser().getSurname());
	   	        return postDto;
	   	    });
	   	    return new ResponseEntity<>(postDtos, HttpStatus.OK);
	    } else {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	 
	}


	
	
	@GetMapping("/search")
	public ResponseEntity<List<PostDto>> getBySearch(@RequestParam String search){
		
		 List<Post> posts = postService.findBySearch(search);
		 
		 return new ResponseEntity<>(toPostDto.convert(posts),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('SELLER')")
	@GetMapping("/getBySeller/{username}")
	public ResponseEntity<List<PostDtoSeller>> getBySeller(@PathVariable String username){
		
		User user = userService.findByUsername(username);
		if(user==null) {
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		 List<Post> posts = postService.findBySeller(username);
		 if(posts!=null) {
			 return new ResponseEntity<>(toPostDto.convertForSeller(posts),HttpStatus.OK);
		 } else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
		 
	}
	
	
	
	@PreAuthorize("hasRole('SELLER')")
	@GetMapping("/getBestSellers/{username}")
	public ResponseEntity<List<PostDtoSeller>> getBestSellers(@PathVariable String username){
		 List<Post> posts = postService.findBestSelling(username);
		 
		 if(posts!=null) {
			 return new ResponseEntity<>(toPostDto.convertForSeller(posts),HttpStatus.OK);
		 } else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	
	
	@PreAuthorize("hasRole('SELLER')")	
	@Transactional
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String,String>> delete(@PathVariable Long id){
		Map<String,String> result = new HashMap<>();
		
		Post post = postService.findOne(id);

		reviewService.deleteByPost(id);
		
		if(post==null) {
			result.put("message","Post doesn't exist");
		} else {
			post.setDeleted(true);
			postService.save(post);
			result.put("message","Post deleted sucessfully");
		}

	     return new ResponseEntity<>(result,HttpStatus.OK);
	}

	
	
	@PreAuthorize("hasRole('SELLER')")
	@PostMapping("/create")
	public ResponseEntity<Map<String, String>> create(@RequestBody PostDto dto, UriComponentsBuilder uriBuilder){
		User user = userService.findByUsername(dto.getUsername());
		if(user==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getTitle().isEmpty() || dto.getDescription().isEmpty()) {
			return new ResponseEntity<>(Map.of("message","Title and description must not be empty"),HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPrice()<1.0) { 
		 	return new ResponseEntity<>(Map.of("message","Invalid price, min value $1"),HttpStatus.BAD_REQUEST);
		} 
		
		if(dto.getId()!=null) {
			dto.setId(null);
		}
		Post post = toPost.convert(dto);
		post.setDatePosted(LocalDate.now());
		post.setDeleted(false);
		post.setViews(0);
		post.setUser(user);
	
		postService.save(post);
		
		   String createdResourceUri = uriBuilder.path("/api/posts/{id}")
	                .buildAndExpand(post.getId())
	                .toUriString();
		   Map<String, String> responseBody = Map.of(
	                "uri", createdResourceUri,
	                "message", "Post saved successfully"
	        );

	        
	        return ResponseEntity.created(URI.create(createdResourceUri)).body(responseBody);
	}
	
	
	
	@PreAuthorize("hasRole('SELLER')")
	@PutMapping("/update")
	public ResponseEntity<Map<String, String>> update(@RequestBody PostDto dto){
		Post post = toPost.convert(dto);
		if(post==null) {
			return new ResponseEntity<>(Map.of("message","Post doesn't exist"),HttpStatus.BAD_REQUEST);
		}
		if(dto.getId()==null) {
			return new ResponseEntity<>(Map.of("message","Post doesn't exist"),HttpStatus.BAD_REQUEST);
		}
		User user = userService.findByUsername(dto.getUsername());
		
		if(user==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getId()!=null && !user.getPosts().contains(post)) {
			return new ResponseEntity<>(Map.of("message","Post doesn't belong to user"),HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getTitle()==null || dto.getDescription()==null ||
				dto.getTitle().isEmpty() || dto.getDescription().isEmpty()) {
			return new ResponseEntity<>(Map.of("message","Title and description must not be empty"),HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPrice()<1.0) { 
		 	return new ResponseEntity<>(Map.of("message","Invalid price, min value $1"),HttpStatus.BAD_REQUEST);
		} 
		
		post.setUser(user);
	
		postService.save(post);
		 
		return new ResponseEntity<>(Map.of("message","Changes saved sucessfully"),HttpStatus.OK);
	}

	
}
