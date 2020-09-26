package com.finalproject.ragil.finalproject.restApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.finalproject.ragil.finalproject.entity.RequestTransaction;
import com.finalproject.ragil.finalproject.usecase.ProviderUsecase;

@RestController
public class ProviderController {
	
	@Autowired
	ProviderUsecase usecase;
	
	@RequestMapping(value = "/products",method = RequestMethod.GET)
	public ResponseEntity<?> showAllProduct(){
		return usecase.showProduct();
	}
	
	@RequestMapping(value = "/products/{type}",method = RequestMethod.GET)
	public ResponseEntity<?> showAllProduct(@PathVariable("type") String type){
		return usecase.showProductByType(type);
	}
	
	@RequestMapping(value = "/products/{type}/{price}",method = RequestMethod.GET)
	public ResponseEntity<?> showProductByPrice(@PathVariable("type") String type, @PathVariable("price") int price){
		return usecase.showProductByPrice(type,price);
	}
	
	@RequestMapping(value = "/cardnumber",method = RequestMethod.GET)
	public ResponseEntity<?> chekNoHandphone(@RequestParam String no){
		return usecase.checkNoHandPhone(no);
	}
	
	@RequestMapping(value = "/transaction",method = RequestMethod.POST)
	public ResponseEntity<?> beliPaket(@RequestBody RequestTransaction transaction){
		return usecase.transaksiUsecase(transaction);
	}

}
