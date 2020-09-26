package com.finalproject.ragil.finalproject.enitity.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PinRequest {

	@NotNull(message = "pin tidak boleh kosong")
	@Size(min = 6, max = 6, message = "pin harus 6 karakter")
	private String pin;

	public PinRequest() {
		super();
	}

	public PinRequest(String pin) {
		super();
		this.pin = pin;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

}
