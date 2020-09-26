package com.finalproject.ragil.finalproject.enitity.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransactionRequest {
	
	@NotNull(message = "nominal tidak boleh kosong")
	private int nominal;
	
	@NotNull(message = "pin tidak boleh kosong")
	@Size(min = 6, max = 6, message = "pin harus 6 karakter")
	private String pin;

	public TransactionRequest(@NotNull(message = "nominal tidak boleh kosong") int nominal,
			@NotNull(message = "pin tidak boleh kosong") @Size(min = 6, max = 6, message = "pin harus 6 karakter") String pin) {
		super();
		this.nominal = nominal;
		this.pin = pin;
	}

	public TransactionRequest() {
		super();
	}

	public int getNominal() {
		return nominal;
	}

	public void setNominal(int nominal) {
		this.nominal = nominal;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

}
