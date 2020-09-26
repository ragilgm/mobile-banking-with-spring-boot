package com.finalproject.ragil.finalproject.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import com.finalproject.ragil.finalproject.enitity.Transaction;
import com.finalproject.ragil.finalproject.enitity.request.RegisterRequest;
import com.finalproject.ragil.finalproject.util.ConvertData;
@Component
public class MailConstructor {
	@Autowired
	private Environment env;
	
	@Autowired
	ConvertData convert;

	@Autowired 
	private OtpGenerator otpGenerator;
	
	public SimpleMailMessage verificationMbankingRegister(RegisterRequest account) {
        // generate otp
        Integer otpValue = otpGenerator.generateOTP(account.getUsernameid());
        if (otpValue == -1)
        {
            System.out.println("otp not working");
        }
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(account.getEmail());
		email.setSubject("aktivasi mbanking @mybanking");
		email.setText("OTP : " + otpValue);
		email.setFrom(env.getProperty("support.email"));
		return email;
	}
	
	public SimpleMailMessage transactionMail(String email,Transaction transaction) {
		int total = transaction.getTax()+transaction.getAmmount();
		String currencyIDR = convert.currencyIDR(total);
		String ammount = convert.currencyIDR(transaction.getAmmount());
		String tax = convert.currencyIDR(transaction.getTax());
		LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String formattedDate = myDateObj.format(myFormatObj);
		
		SimpleMailMessage simpleMail = new SimpleMailMessage();
		simpleMail.setTo(email);
		simpleMail.setSubject("transaksi mbanking @mybanking");


		simpleMail.setText("Transaction \n"
				+ "==========================================\n"
				+ "merchant name : "+transaction.getMerchantname()+"\n"
				+ "ammount : "+ammount+"\n"
				+ "tax : "+tax+"\n"
				+ "transaction date : "+formattedDate+"\n"
				+ "total : "+ currencyIDR+"\n"
				+ "status : berhasil");
		
		simpleMail.setFrom(env.getProperty("support.email"));
		return simpleMail;
	}
	
}
