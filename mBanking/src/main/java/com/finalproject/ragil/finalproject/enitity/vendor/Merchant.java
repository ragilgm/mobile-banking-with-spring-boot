package com.finalproject.ragil.finalproject.enitity.vendor;

public class Merchant {

	private int merchantId;
	private String merhantname;

	public Merchant() {
	}

	public Merchant(int merchantId, String merhantname) {
		super();
		this.merchantId = merchantId;
		this.merhantname = merhantname;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerhantname() {
		return merhantname;
	}

	public void setMerhantname(String merhantname) {
		this.merhantname = merhantname;
	}

}
