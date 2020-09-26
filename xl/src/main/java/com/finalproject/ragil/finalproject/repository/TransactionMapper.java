package com.finalproject.ragil.finalproject.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.finalproject.ragil.finalproject.entity.Transaction;

@Mapper
public interface TransactionMapper {

	
	@Insert("insert into transaction (id_product,id_cardnumber,total,date) values (#{id_product},#{id_cardnumber},#{total},#{date})")
	int insertTransaksi(Transaction transaction);
}
