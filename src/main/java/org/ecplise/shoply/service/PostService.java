package org.ecplise.shoply.service;

import java.util.List;
import java.util.Optional;

import org.eclipse.shoply.enumeration.PostCategory;
import org.eclipse.shoply.model.Post;
import org.springframework.data.domain.Page;

public interface PostService {

    Post findOne(Long id);

    List<Post> findAll();

    Post save(Post post);

    Post delete(Long id);

	List<Post> sortByDate(List<Post> posts);

	Page<Post> findByCategory(PostCategory postCategory, int page);
	
	List<Post> findBySearch(String search);

	List<Post> findBySeller(String username);

	List<Post> findBestSelling(String username);

	List<Post> findAllBySeller(String username);

}
