package org.eclipse.shoply.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.shoply.model.Sale;
import org.eclipse.shoply.web.dto.SaleDto;
import org.ecplise.shoply.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class SaleDtoToSale implements Converter<SaleDto, Sale> {

	
	@Autowired
	private SaleService saleService;
	
	@Override
	public Sale convert(SaleDto dto) {
		Long id = dto.getId();
		
		Sale sale = id==null? new Sale(): saleService.findOne(id).get();
		
		if (sale !=null) 
			sale.setId(dto.getId());{
			sale.setDateAndTime(dto.getDateAndTime());
		}
			
			return sale;
	}

	public List<Sale> convert(List<SaleDto> sales) {
		List<Sale> result = new ArrayList<Sale>();
		for (SaleDto sale : sales) {
			result.add(convert(sale));
		}
		return result;
	}

}
