package com.finalproject.ragil.finalproject.entity;

public class Product {

	private int id;
	private String type;
	private int price;
	private String description;

	public Product() {
	}

	public Product(int id, String type, int price, String description) {
		super();
		this.id = id;
		this.type = type;
		this.price = price;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
