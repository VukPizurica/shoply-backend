package org.eclipse.shoply.repository;

import java.util.List;

import org.eclipse.shoply.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

	List<Review> deleteByPostId(Long id);
	
}
