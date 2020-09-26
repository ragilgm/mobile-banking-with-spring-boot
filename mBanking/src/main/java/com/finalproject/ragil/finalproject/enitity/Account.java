package com.finalproject.ragil.finalproject.enitity;

import java.util.Date;

public class Account {

	private int id;

	private String accountnumber;

	private String FirstName;

	private String LastName;

	private Date dateofbirth;

	private String address1;

	private String address2;

	private String city;

	private String state;

	private String postalcode;

	private String country;

	private String mobile;

	private String email;

	private String ektp;

	private String pin;

	private int balance;

	public Account() {
		super();
	}

	public Account(String accountnumber, String firstName, String lastName, Date dateofbirth, String address1,
			String address2, String city, String state, String postalcode, String country, String mobile, String email,
			String ektp, String pin, int balance) {
		super();

		this.accountnumber = accountnumber;
		FirstName = firstName;
		LastName = lastName;
		this.dateofbirth = dateofbirth;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.postalcode = postalcode;
		this.country = country;
		this.mobile = mobile;
		this.email = email;
		this.ektp = ektp;
		this.pin = pin;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEktp() {
		return ektp;
	}

	public void setEktp(String ektp) {
		this.ektp = ektp;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

}
