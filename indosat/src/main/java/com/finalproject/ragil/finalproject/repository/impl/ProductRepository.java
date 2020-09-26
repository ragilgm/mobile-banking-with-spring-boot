package com.finalproject.ragil.finalproject.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.ragil.finalproject.entity.Product;
import com.finalproject.ragil.finalproject.repository.ProductMapper;

@Service
public class ProductRepository implements ProductMapper{

	@Autowired
	ProductMapper productMapper;
	
	@Override
	public List<Product> listProduct() {
		return productMapper.listProduct();
	}

	@Override
	public List<Product> listProductByPaket(Product product) {
		return productMapper.listProductByPaket(product);
	}

	@Override
	public Product singleProduct(Product product) {
		return productMapper.singleProduct(product);
	}

	@Override
	public Product singleProductByPrice(Product product) {
		return productMapper.singleProductByPrice(product);
	}

}
