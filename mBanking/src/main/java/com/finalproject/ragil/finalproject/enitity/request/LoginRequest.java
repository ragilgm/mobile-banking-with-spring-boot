package com.finalproject.ragil.finalproject.enitity.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginRequest {

	@NotNull(message = "usernameid tidak boleh kosong")
	@Size(min = 5, max = 10, message = "usernameid min 5, vax 10")
	private String usernameid;
	
	@NotNull(message = "mpin tidak boleh kosong")
	@Size(min = 5, max = 6, message = "mpin harus 6 karakter")
	private String mpin;

	public LoginRequest() {
		super();
	}

	public LoginRequest(String usernameid, String mpin) {
		super();
		this.usernameid = usernameid;
		this.mpin = mpin;
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
