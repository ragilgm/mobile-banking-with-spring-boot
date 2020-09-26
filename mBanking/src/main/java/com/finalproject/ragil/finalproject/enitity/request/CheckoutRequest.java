package com.finalproject.ragil.finalproject.enitity.request;
import com.finalproject.ragil.finalproject.enitity.vendor.Merchant;

public class CheckoutRequest {
	private String usernameid;
	private PinRequest pin;
	private String type;
	private int price;
	private String no_hp;
	private Merchant merchant;

	public CheckoutRequest() {
		super();
	}

	public CheckoutRequest(String usernameid, PinRequest pin, String type, int price, String no_hp, Merchant merchant) {
		super();
		this.usernameid = usernameid;
		this.pin = pin;
		this.type = type;
		this.price = price;
		this.no_hp = no_hp;
		this.merchant = merchant;
	}

	public String getUsernameid() {
		return usernameid;
	}

	public void setUsernameid(String usernameid) {
		this.usernameid = usernameid;
	}

	public PinRequest getPin() {
		return pin;
	}

	public void setPin(PinRequest pin) {
		this.pin = pin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getNo_hp() {
		return no_hp;
	}

	public void setNo_hp(String no_hp) {
		this.no_hp = no_hp;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

}
