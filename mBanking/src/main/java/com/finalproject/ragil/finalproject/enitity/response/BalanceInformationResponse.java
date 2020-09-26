package com.finalproject.ragil.finalproject.enitity.response;

import java.util.Date;

public class BalanceInformationResponse {

	private Date date;
	private String accountNumber;
	private String fullname;
	private int balance;
	
	public BalanceInformationResponse() {
		super();
	}
	
	public BalanceInformationResponse(Date date, String accountNumber, String fullname, int balance) {
		super();
		this.date = date;
		this.accountNumber = accountNumber;
		this.fullname = fullname;
		this.balance = balance;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
}
