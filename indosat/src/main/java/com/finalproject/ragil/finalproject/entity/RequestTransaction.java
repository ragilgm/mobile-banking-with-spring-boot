package com.finalproject.ragil.finalproject.entity;

public class RequestTransaction {

	private String no_hp;
	private String type;
	private int id;

	public RequestTransaction() {
	}

	public RequestTransaction(String no_hp, String type, int id) {
		super();
		this.no_hp = no_hp;
		this.type = type;
		this.id = id;
	}

	public String getNo_hp() {
		return no_hp;
	}

	public void setNo_hp(String no_hp) {
		this.no_hp = no_hp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
