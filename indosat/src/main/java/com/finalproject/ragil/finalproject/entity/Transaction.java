package com.finalproject.ragil.finalproject.entity;

import java.util.Date;

public class Transaction {

	private int id;
	private int id_product;
	private int id_cardnumber;
	private int total;
	private Date date;

	public Transaction() {
	}

	public Transaction(int id, int id_product, int id_cardnumber, int total, Date date) {
		super();
		this.id = id;
		this.id_product = id_product;
		this.id_cardnumber = id_cardnumber;
		this.total = total;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_product() {
		return id_product;
	}

	public void setId_product(int id_product) {
		this.id_product = id_product;
	}

	public int getId_cardnumber() {
		return id_cardnumber;
	}

	public void setId_cardnumber(int id_cardnumber) {
		this.id_cardnumber = id_cardnumber;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
