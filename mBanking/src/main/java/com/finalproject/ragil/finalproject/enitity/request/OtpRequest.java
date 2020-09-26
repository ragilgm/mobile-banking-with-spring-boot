package com.finalproject.ragil.finalproject.enitity.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OtpRequest {
	
	@NotNull(message = "otp tidak boleh kosong")
	@Min(value = 6,message="panjang otp number adalah 6 digit")
	private int otp;


	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}
	
	

}
