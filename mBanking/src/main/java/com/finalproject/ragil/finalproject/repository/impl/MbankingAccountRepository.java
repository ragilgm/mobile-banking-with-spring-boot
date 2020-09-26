package com.finalproject.ragil.finalproject.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.finalproject.ragil.finalproject.enitity.Account;
import com.finalproject.ragil.finalproject.enitity.MbankingAccount;
import com.finalproject.ragil.finalproject.enitity.Transaction;
import com.finalproject.ragil.finalproject.repository.MBankingAccountMapper;

@Service
@Repository
public class MbankingAccountRepository implements MBankingAccountMapper{
	
	@Autowired
	MBankingAccountMapper mBankingRepo;
	

	@Override
	public int registerMbankingAccount(MbankingAccount account) {
		return mBankingRepo.registerMbankingAccount(account);
	}

	@Override
	public Account checkAccount(MbankingAccount account) {
		return mBankingRepo.checkAccount(account);
	}

	@Override
	public MbankingAccount accountLogin(MbankingAccount account) {
		return mBankingRepo.accountLogin(account);
	}

	@Override
	public int updateMbankingAccount(MbankingAccount account) {
		return mBankingRepo.updateMbankingAccount(account);
	}

	@Override
	public Account accountInformation(MbankingAccount accont) {
		return mBankingRepo.accountInformation(accont);
	}

	@Override
	public MbankingAccount userInformation(String usernameid,String pin) {
		return mBankingRepo.userInformation(usernameid,pin);
	}

	@Override
	public int transaction(Transaction transaction) {
		return mBankingRepo.transaction(transaction);
	}

	@Override
	public int updateBalance(int balance, String accountnumber) {
		return mBankingRepo.updateBalance(balance, accountnumber);
	}

	@Override
	public Account checkMobileBankingAccount(MbankingAccount account) {
		return mBankingRepo.checkMobileBankingAccount(account);
	}

	public MbankingAccount findByUsername(String username) {
		return mBankingRepo.findByUsername(username);
	}


}
