package com.finalproject.ragil.finalproject.enitity.request;

public class SearchProviderRequest {
	
	private String phoneNumber;
	
	

	public SearchProviderRequest() {
		super();
	}

	public SearchProviderRequest(String phoneNumber) {
		super();
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
