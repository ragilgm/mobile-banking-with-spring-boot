package com.finalproject.ragil.finalproject.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.ragil.finalproject.entity.Transaction;
import com.finalproject.ragil.finalproject.repository.TransactionMapper;

@Service
public class TransactionRepository implements TransactionMapper{

	@Autowired
	TransactionMapper transaksiMapper;
	
	@Override
	public int insertTransaksi(Transaction transaksi) {
		return transaksiMapper.insertTransaksi(transaksi);
	}
	

}
