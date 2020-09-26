package com.finalproject.ragil.finalproject.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.finalproject.ragil.finalproject.entity.CardNumber;

@Mapper
public interface CardNumberMapper {

	@Select("select * from cardnumber where no_hp=#{no_hp}")
	CardNumber seachNoHandphone(CardNumber cardNumber);
}
