package com.finalproject.ragil.finalproject.enitity;

import java.util.Date;

public class Transaction {

	private int transactionid;
	private int accountid;
	private int merchantid;
	private String merchantname;
	private int ammount;
	private int tax;
	private Date date;

	public Transaction() {
		super();
	}

	public Transaction(int accountid, int merchantid, String merchantname, int ammount, int tax, Date date) {
		super();
		this.accountid = accountid;
		this.merchantid = merchantid;
		this.merchantname = merchantname;
		this.ammount = ammount;
		this.tax = tax;
		this.date = date;
	}

	public int getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}

	public int getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(int merchantid) {
		this.merchantid = merchantid;
	}

	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public int getAmmount() {
		return ammount;
	}

	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
