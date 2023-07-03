package org.ecplise.shoply.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.model.Sale;
import org.eclipse.shoply.web.dto.PostOrderDto;
import org.eclipse.shoply.web.dto.SaleDto;

public interface SaleService {

    Optional<Sale> findOne(Long id);

    List<Sale> findAll();

    void delete(Long id);
	
	List<Sale> findByPost(Post post);

	Map<String,Object> analyticsEarnings(String username, int year);
	
	Map<String,Object> customerAnalytics(String username);
	
	List<SaleDto> findByUser(String username);
	
    Sale save(PostOrderDto orderPosts);
	
    boolean checkForReview(String username, Long postId);


}
