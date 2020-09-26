package com.finalproject.ragil.finalproject.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.finalproject.ragil.finalproject.enitity.MbankingAccount;
import com.finalproject.ragil.finalproject.repository.impl.MbankingAccountRepository;


@Service
public class MyUserDetailServices implements UserDetailsService {

	@Autowired
	MbankingAccountRepository mBankingRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MbankingAccount data = mBankingRepo.findByUsername(username);
		return new User(data.getUsernameid(), data.getMpin(), new ArrayList<>());
	}

}
