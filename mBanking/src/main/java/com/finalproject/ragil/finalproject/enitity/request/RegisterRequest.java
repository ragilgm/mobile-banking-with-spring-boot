package com.finalproject.ragil.finalproject.enitity.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterRequest {


	@NotNull(message = "pin tidak boleh kosong")
	@Size(min = 10, max = 10, message = "accountnumber harus 10 caracter")
	private String accountnumber;

	@NotNull(message = "pin tidak boleh kosong")
	@Size(min = 6, max = 6, message = "pin harus 6 caracter")
	private String pin;

	@NotNull(message = "email tidak boleh kosong")
	@Email(message = "email harus valid")
	private String email;

	@NotNull(message = "ektp tidak boleh kosong")
	@Size(min = 15, max = 20, message = "ektp minimal 15 sampai 20 caracter")
	private String ektp;

	@NotNull(message = "usernameid tidak boleh kosong")
	@Size(min = 5, max = 15, message = "usernameid minimal panjang 5 karakter dan maksimal 15 karakter")
	private String usernameid;

	@NotNull(message = "mpin tidak boleh kosong")
	@Size(min = 6, max = 6, message = "mpin harus 6 caracter")
	private String mpin;

	public RegisterRequest(
			@NotNull(message = "pin tidak boleh kosong") @Size(min = 10, max = 10, message = "accountnumber harus 10 caracter") String accountnumber,
			@NotNull(message = "pin tidak boleh kosong") @Size(min = 6, max = 6, message = "pin harus 6 caracter") String pin,
			@NotNull(message = "email tidak boleh kosong") @Email(message = "email harus valid") String email,
			@NotNull(message = "ektp tidak boleh kosong") @NotNull(message = "dateofbirth tidak boleh kosong") String ektp,
			@NotNull(message = "usernameid tidak boleh kosong") String usernameid,
			@NotNull(message = "mpin tidak boleh kosong") @Size(min = 6, max = 6, message = "pin harus 6 caracter") String mpin) {
		super();
		this.accountnumber = accountnumber;
		this.pin = pin;
		this.email = email;
		this.ektp = ektp;
		this.usernameid = usernameid;
		this.mpin = mpin;
	}

	public RegisterRequest() {
		super();
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
	
	

}
