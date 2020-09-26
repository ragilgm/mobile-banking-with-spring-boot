package com.finalproject.ragil.finalproject.enitity;

import java.util.Date;

public class MbankingAccount {

	private int id;
	private int accountid;
	private String accountnumber;
	private String pin;
	private String email;
	private Date dateofbirth;
	private String ektp;
	private String usernameid;
	private String mpin;
	private String status;

	public MbankingAccount() {
		super();
	}

	public MbankingAccount(int accountid, String accountnumber, String pin, String email, Date dateofbirth,
			String ektp, String usernameid, String mpin, String status) {
		super();
		this.accountid = accountid;
		this.accountnumber = accountnumber;
		this.pin = pin;
		this.email = email;
		this.dateofbirth = dateofbirth;
		this.ektp = ektp;
		this.usernameid = usernameid;
		this.mpin = mpin;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getEktp() {
		return ektp;
	}

	public void setEktp(String ektp) {
		this.ektp = ektp;
	}

	public String getUsernameid() {
		return usernameid;
	}

	public void setUsernameid(String usernameid) {
		this.usernameid = usernameid;
	}

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
