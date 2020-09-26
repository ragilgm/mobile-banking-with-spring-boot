package com.finalproject.ragil.finalproject.enitity.request;


public class BalanceInformationRequest {
	
	private String usernameid;
	private String pin;

	public BalanceInformationRequest() {
		super();
	}

	public BalanceInformationRequest(String usernameid, String pin) {
		super();
		this.usernameid = usernameid;
		this.pin = pin;
	}

	public String getUsernameid() {
		return usernameid;
	}

	public void setUsernameid(String usernameid) {
		this.usernameid = usernameid;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

}
