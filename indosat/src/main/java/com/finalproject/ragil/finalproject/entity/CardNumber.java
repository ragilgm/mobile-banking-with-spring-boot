package com.finalproject.ragil.finalproject.entity;

public class CardNumber {
	
	private int id;
	private String no_hp;
	private String status;
	
	public CardNumber(int id, String no_hp, String status) {
		this.id = id;
		this.no_hp = no_hp;
		this.status = status;
	}

	public CardNumber() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNo_hp() {
		return no_hp;
	}

	public void setNo_hp(String no_hp) {
		this.no_hp = no_hp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
