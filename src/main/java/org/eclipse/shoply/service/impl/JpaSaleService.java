package org.eclipse.shoply.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.model.Sale;
import org.eclipse.shoply.model.User;
import org.eclipse.shoply.repository.SaleRepository;
import org.eclipse.shoply.support.SaleToSaleDto;
import org.eclipse.shoply.web.dto.PostOrderDto;
import org.eclipse.shoply.web.dto.SaleDto;
import org.ecplise.shoply.service.PostService;
import org.ecplise.shoply.service.SaleService;
import org.ecplise.shoply.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaSaleService implements SaleService{

	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private SaleToSaleDto toSaleDto;

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserServices userService;
	
	public Optional<Sale> findOne(Long id) {
		
		return saleRepository.findById(id);
	}

	public List<Sale> findAll() {
		
		return saleRepository.findAll();
	}

	public Sale save(PostOrderDto orderPosts) {
	    Sale sale = new Sale();
	    List<Post> posts = new ArrayList<>();
	    User user = userService.findByUsername(orderPosts.getUsername());

	    if(user==null) {
        	return null;
        }
	    
	    for (Map.Entry<Long, Integer> entry : orderPosts.getPosts().entrySet()) {
	        Long postId = entry.getKey();
	        Integer amount = entry.getValue();

	        Post post = postService.findOne(postId);
	        if(post==null) {
	        	return null;
	        }
	        for(int i=0;i<amount;i++) {
	        	posts.add(post);
	        }
	    }
	    
	    for(Post post:posts) {
	    	post.getSales().add(sale);
	    }
	    
	    sale.setDateAndTime(LocalDateTime.now());
	    sale.setPosts(posts);
	    sale.setUser(user);

	    return saleRepository.save(sale);
	}


	public void delete(Long id) {
		saleRepository.deleteById(id);
		
	}
	

	
	public List<Sale> findByPost(Post post){
		return saleRepository.findByPosts(post);
	}

	public Map<String, Object> analyticsEarnings(String username, int year) {
		 String[] monthLabels = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

		    List<Post> posts = postService.findAllBySeller(username);
		    List<Long> postIds = posts.stream().map(Post::getId).collect(Collectors.toList());

		    List<Sale> sales = saleRepository.findByYear(year);
		    List<SaleDto> salesDtoList = toSaleDto.convert(sales);

		    Map<String, Double> monthlyEarnings = new LinkedHashMap<>();
		    for (String monthLabel : monthLabels) {
		        monthlyEarnings.put(monthLabel, 0.0);
		    }

		    for (SaleDto saleDto : salesDtoList) {
		        LocalDateTime date = saleDto.getDateAndTime();
		        if (date.getYear() == year) {
		            Map<Long, Double> amounts = saleDto.getAmounts();
		            Double saleEarnings = 0.0;
		            Set<Long> addedPostIds = new HashSet<>();

		            for (Long postId : postIds) {
		                Double amount = amounts.get(postId);
		                if (amount != null && !addedPostIds.contains(postId)) {
		                    saleEarnings += amount;
		                    addedPostIds.add(postId);
		                }
		            }

	            String monthLabel = monthLabels[date.getMonthValue() - 1];
	            Double previousEarnings = monthlyEarnings.get(monthLabel);
	            Double newEarnings = previousEarnings + saleEarnings;
	            newEarnings = Double.parseDouble(String.format("%.2f", newEarnings).replace(",", "."));
	            monthlyEarnings.put(monthLabel, newEarnings);
	        }
	    }

	    Map<String, Object> result = new HashMap<>();
	    result.put("labels", monthLabels);
	    result.put("data", monthlyEarnings.values().toArray());

	    return result;
	}




	//fix duplicate values
	public Map<String, Object> customerAnalytics(String username) {
	    String[] genderLabels = {"Male", "Female"};

	    Set<String> genderUsernameList = new HashSet<>();

	    Map<String, Integer> genderCounts = new LinkedHashMap<>();
	    for (String genderLabel : genderLabels) {
	        genderCounts.put(genderLabel, 0);
	    }

	    List<Post> posts = postService.findAllBySeller(username);
	    List<Sale> sales = new ArrayList<>();
	    for (Post post : posts) {
	        List<Sale> salesByPosts = saleRepository.findByPosts(post);
	        sales.addAll(salesByPosts);
	    }

	    List<SaleDto> salesDto = toSaleDto.convert(sales);
	    for (SaleDto dto : salesDto) {
	        String user = dto.getUsername();
	        if (!genderUsernameList.contains(user)) {
	            genderUsernameList.add(user);
	            String userGender = dto.getUserGender();
	            if (!genderCounts.containsKey(userGender)) {
	                throw new RuntimeException("Gender count not found for user gender: " + userGender);
	            }
	            int genderCount = genderCounts.get(userGender);
	            genderCounts.put(userGender, ++genderCount);
	        }
	    }


	    Map<String, Object> result = new LinkedHashMap<>();
	    result.put("labels", genderLabels);
	    result.put("data", new ArrayList<>(genderCounts.values()));
	    return result;
	}



	@Override
	public List<SaleDto> findByUser(String username) {
		
		  List<Post> posts = postService.findBySeller(username);
		    List<Sale> sales = new ArrayList<>();
		    
		    for(Post post:posts) {
		    	List<Sale> salesByPosts = saleRepository.findByPosts(post);
		    	for(Sale sale: salesByPosts) {
		    		sales.add(sale);
		    	}
		    }
		  
		return toSaleDto.convert(sales);
	}

	@Override
	public boolean checkForReview(String username, Long postId) {
		User user = userService.findByUsername(username);
		Post bought = postService.findOne(postId);
		List<Sale> sales = user.getSales();
		for(Sale sale: sales) {
			for(Post post:sale.getPosts()) {
				if(post.getId()==bought.getId()) {
					return true;
				}	
			}
		}
		return false;
	}


	

	

}
