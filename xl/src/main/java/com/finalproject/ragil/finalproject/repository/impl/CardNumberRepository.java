package com.finalproject.ragil.finalproject.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.ragil.finalproject.entity.CardNumber;
import com.finalproject.ragil.finalproject.repository.CardNumberMapper;

@Service
public class CardNumberRepository implements CardNumberMapper{

	@Autowired
	CardNumberMapper noHpMapper;
	
	@Override
	public CardNumber seachNoHandphone(CardNumber noHandphone) {
		return noHpMapper.seachNoHandphone(noHandphone);
	}

}
