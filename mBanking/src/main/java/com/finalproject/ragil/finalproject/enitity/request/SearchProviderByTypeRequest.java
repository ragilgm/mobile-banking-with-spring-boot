package com.finalproject.ragil.finalproject.enitity.request;

public class SearchProviderByTypeRequest {

	private String phoneMumber;
	private String type;

	public SearchProviderByTypeRequest(String phoneMumber, String type) {
		super();
		this.phoneMumber = phoneMumber;
		this.type = type;
	}

	public SearchProviderByTypeRequest() {
		super();
	}

	public String getPhoneMumber() {
		return phoneMumber;
	}

	public void setPhoneMumber(String phoneMumber) {
		this.phoneMumber = phoneMumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
