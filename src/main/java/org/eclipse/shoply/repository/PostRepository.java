package org.eclipse.shoply.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.eclipse.shoply.enumeration.PostCategory;
import org.eclipse.shoply.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	List<Post> findByDeletedFalse();	
	
	Page<Post> findByDeletedFalseAndCategory(PostCategory postCategory, Pageable pageable);

	List<Post> findByDeletedFalseAndTitleContaining(String search);
	
	List<Post> findByDeletedFalseAndUserId(Long userId);
	
	List<Post> findByUserId(Long userId);


	
	
}
