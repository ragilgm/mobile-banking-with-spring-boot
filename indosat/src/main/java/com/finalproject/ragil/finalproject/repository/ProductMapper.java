package com.finalproject.ragil.finalproject.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.finalproject.ragil.finalproject.entity.Product;

@Mapper
public interface ProductMapper {

	@Select("select * from product")
	@Results(value = {
			@Result(property = "id",column = "id"),
			@Result(property = "type",column = "type"),
			@Result(property = "price",column = "price"),
			@Result(property = "description",column = "description"),
	})
	List<Product> listProduct();
	
	@Select("select * from product where type=#{type}")
	@Results(value = {
			@Result(property = "id",column = "id"),
			@Result(property = "type",column = "type"),
			@Result(property = "price",column = "price"),
			@Result(property = "description",column = "description"),
	})
	List<Product> listProductByPaket(Product product);
	
	@Select("select * from product where type=#{type} and id=#{id}")
	Product singleProduct(Product product);
	
	@Select("select * from product where type=#{type} and price=#{price}")
	Product singleProductByPrice(Product product);
}
