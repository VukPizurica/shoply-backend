package org.eclipse.shoply.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.shoply.model.Sale;
import org.eclipse.shoply.support.SaleToSaleDto;
import org.eclipse.shoply.web.dto.PostOrderDto;
import org.eclipse.shoply.web.dto.SaleDto;
import org.ecplise.shoply.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/sales", produces = MediaType.APPLICATION_JSON_VALUE)
public class SaleController {

	
	@Autowired
	private SaleService saleService;
	

	@Autowired
	private SaleToSaleDto toSaleDto;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<SaleDto> getOne(@PathVariable Long id) {
		Sale sale = saleService.findOne(id).get();
		if(sale==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(toSaleDto.convert(sale),HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasRole('SELLER')")
	@GetMapping("/analytics/earnings/{username}")
	public ResponseEntity<Map<String, Object>> getEarnings(@PathVariable String username, @RequestParam int year) {
	     return new ResponseEntity<>(saleService.analyticsEarnings(username,year),HttpStatus.OK);
	        
	}
	
	
	
	@PreAuthorize("hasRole('SELLER')")
	@GetMapping("/analytics/customerAnalytics/{username}")
	public ResponseEntity<Map<String, Object>> getCustomerAnalytics(@PathVariable String username) {
	     return new ResponseEntity<>(saleService.customerAnalytics(username),HttpStatus.OK);
	        
	}
	
	
	
	@GetMapping("/getByUser/{username}")
	public ResponseEntity<List<SaleDto>>getByUser(@PathVariable String username) { 
	     return new ResponseEntity<>(saleService.findByUser(username),HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasAnyRole('SELLER', 'BUYER')")
	@PostMapping("/create")
	public ResponseEntity<Map<String, String>> create(@RequestBody PostOrderDto orderPosts){
		Map<String,String> result = new HashMap<>();
		Sale sale = saleService.save(orderPosts);
		
		if(sale!=null) {
			result.put("message", "thank you for your order!");
		} else {
			result.put("message", "something went wrong");
		}
		
		return new ResponseEntity<>(result,HttpStatus.CREATED);
	}
	
	
	
	@GetMapping("/checkForReview")
	public ResponseEntity<Boolean>checkForReview(@RequestParam String username,@RequestParam Long postId){
		
		boolean check= saleService.checkForReview(username, postId);
		
		 return new ResponseEntity<>(check, HttpStatus.OK);
	}
}
