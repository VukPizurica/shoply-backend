package org.eclipse.shoply.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.shoply.model.Post;
import org.eclipse.shoply.model.Sale;
import org.eclipse.shoply.web.dto.SaleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SaleToSaleDto implements Converter<Sale, SaleDto> {

	@Override
	public SaleDto convert(Sale sale) {
		SaleDto dto = new SaleDto();
		
		dto.setId(sale.getId());
		dto.setDateAndTime(sale.getDateAndTime());
		dto.setUsername(sale.getUser().getUsername());
		List<Post> posts = sale.getPosts();
		Map<Long,Double> postAmounts = new HashMap<>();
		for(Post post:posts) {
			if(postAmounts.get(post.getId())!=null) {
				postAmounts.merge(post.getId(),post.getPrice() , (v, n) -> v + n);
			
			} else {
				postAmounts.put(post.getId(), post.getPrice());
			}
		}
		dto.setAmounts(postAmounts);
		dto.setUserGender(sale.getUser().getGender());
		return dto;
	}

	public List<SaleDto> convert(List<Sale> sales) {
		List<SaleDto> result = new ArrayList<SaleDto>();
		for (Sale sale : sales) {
			result.add(convert(sale));
		}
		return result;
	}

}
