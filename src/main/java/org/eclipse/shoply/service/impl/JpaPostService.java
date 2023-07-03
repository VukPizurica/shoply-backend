package org.eclipse.shoply.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.shoply.enumeration.PostCategory;
import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.model.User;
import org.eclipse.shoply.repository.PostRepository;
import org.ecplise.shoply.service.PostService;
import org.ecplise.shoply.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JpaPostService implements PostService {
	
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserServices userService;
	
	public Post  findOne(Long id) {
		Post post = postRepository.findById(id).get();
		if(post.isDeleted()) {
			return null;
		}
		return post;
	}

	public List<Post> findAll() {
		List<Post> posts =postRepository.findByDeletedFalse();
		return posts;
	}

	public Post save(Post post) {
		return postRepository.save(post);
	}

	public Post delete(Long id) {
		Optional<Post> post = postRepository.findById(id);
		if(post.isPresent()) {
			postRepository.deleteById(id);
			return post.get();
		}
		return null;
		
	}

	@Override
	public List<Post> sortByDate(List<Post> posts) {
	    Collections.sort(posts, new Comparator<Post>() {
	        public int compare(Post post1, Post post2) {
	            return post2.getDatePosted().compareTo(post1.getDatePosted());
	        }
	    });
	    return posts;
	}
	
	public Page<Post> findByCategory(PostCategory postCategory, int page){
	    Pageable pageable = PageRequest.of(page, 10);
	    return postRepository.findByDeletedFalseAndCategory(postCategory, pageable);
	}


	@Override
	public List<Post> findBySearch(String search) {
	
	    String[] searchTerms = search.toLowerCase().split("\\s+");
	    List<Post> result = new ArrayList<>();
	    List<Post> posts = postRepository.findByDeletedFalse();

	    for (Post post : posts) {
	        boolean match = true;
	        for (String term : searchTerms) {
	            if (!(post.getTitle().toLowerCase().contains(term)
	                    || post.getDescription().toLowerCase().contains(term)
	                    || post.getCategory().toString().toLowerCase().contains(term))) {
	                match = false;
	                break;
	            }
	        }
	        if (match) {
	            result.add(post);
	        }
	    }
	    return result;
	}

	
	public List<Post> findBySeller(String username){
		User user = userService.findByUsername(username);
		List<Post> posts = new ArrayList<>();
		if(user==null) {
			return posts;
		}
		posts = postRepository.findByDeletedFalseAndUserId(user.getId());
		return posts;
	}
	
	public List<Post> findAllBySeller(String username){
		User user = userService.findByUsername(username);
		List<Post> posts = new ArrayList<>();
		if(user==null) {
			return posts;
		}
		posts = postRepository.findByUserId(user.getId());
		return posts;
	}
	
	
	public List<Post> findBestSelling(String username) {
	     User user = userService.findByUsername(username);
	     List<Post> posts = postRepository.findByDeletedFalseAndUserId(user.getId());
	     return posts.stream()
	          .sorted(Comparator.comparing(post -> post.getSales().size(), Comparator.reverseOrder()))
	          .limit(3)
	          .collect(Collectors.toList());
	}



}
