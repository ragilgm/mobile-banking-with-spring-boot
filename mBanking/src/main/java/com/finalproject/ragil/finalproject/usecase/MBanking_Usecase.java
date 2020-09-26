package com.finalproject.ragil.finalproject.usecase;

import java.io.IOException;
import java.text.ParseException;
import javax.validation.Valid;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.finalproject.ragil.finalproject.enitity.MbankingAccount;
import com.finalproject.ragil.finalproject.enitity.request.BalanceInformationRequest;
import com.finalproject.ragil.finalproject.enitity.request.CheckoutRequest;
import com.finalproject.ragil.finalproject.enitity.request.LoginRequest;
import com.finalproject.ragil.finalproject.enitity.request.OtpRequest;
import com.finalproject.ragil.finalproject.enitity.request.PinRequest;
import com.finalproject.ragil.finalproject.enitity.request.RegisterRequest;
import com.finalproject.ragil.finalproject.enitity.request.SearchProviderByTypeRequest;
import com.finalproject.ragil.finalproject.enitity.request.SearchProviderRequest;
import com.finalproject.ragil.finalproject.enitity.response.BalanceInformationResponse;
import com.finalproject.ragil.finalproject.enitity.vendor.Merchant;
import com.finalproject.ragil.finalproject.enitity.vendor.Product;
import com.finalproject.ragil.finalproject.rabbitController.RabbitController;
import com.finalproject.ragil.finalproject.rabbitmq.front.FrontConsumer;
import com.finalproject.ragil.finalproject.rabbitmq.front.FrontProducer;
import com.finalproject.ragil.finalproject.repository.impl.MbankingAccountRepository;
import com.finalproject.ragil.finalproject.service.MailConstructor;
import com.finalproject.ragil.finalproject.service.OtpGenerator;
import com.finalproject.ragil.finalproject.util.ConvertData;
import com.finalproject.ragil.finalproject.util.EncriptMessage;
import com.finalproject.ragil.finalproject.util.Message;
import com.google.gson.Gson;

@Service
public class MBanking_Usecase {

	@Autowired
	MbankingAccountRepository mBankingRepo;
	@Autowired
	MailConstructor email;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	ConvertData dateFormat;
	@Autowired
	OtpGenerator otpGenerator;
	@Autowired
	RabbitController rabbitController;
	@Autowired
	FrontProducer frontProducer;
	@Autowired
	FrontConsumer frontConsumer;
	// ecrypt message
	@Autowired
	EncriptMessage encryptMessage;
	// security utility
	private final String secretKey = "rahasia";
	MbankingAccount mbankingAccount;
	private Merchant merchant;

	public ResponseEntity<?> mBankingRegistrationUsecase(RegisterRequest register) {

		// multi threading
		Thread thread = new Thread() {
			public void run() {
				rabbitController.Register();
			}
		};
		thread.start();
		
		RegisterRequest request = new RegisterRequest();
		request.setAccountnumber(register.getAccountnumber());
		request.setEktp(register.getEktp());
		request.setEmail(register.getEmail());
		request.setMpin(register.getMpin());
		request.setUsernameid(register.getUsernameid());
		request.setPin(register.getPin());
		
		// convert object to string
		String message = new Gson().toJson(request);

		// encrypt message
		String encrypt = EncriptMessage.encrypt(message, secretKey);
		
		// send data to back
		frontProducer.sendToBack(encrypt);

		// receive data form back
		frontConsumer.recieveFromBack();
		
		String response = frontConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(response, secretKey);

		if (decrypt.equals("FAIL01")) {
			return new ResponseEntity<>(new Message("registrasi gagal, please check kembali data anda"),
					HttpStatus.BAD_REQUEST);
		}
		if (decrypt.equals("FAIL02")) {
			return new ResponseEntity<>(new Message("registrasi gagal"), HttpStatus.BAD_REQUEST);
		}

		if (decrypt.equals("DUPLICATED")) {
			return new ResponseEntity<>(new Message("account sudah terdaftar"), HttpStatus.BAD_REQUEST);
		}

		SimpleMailMessage emailSend = email.verificationMbankingRegister(register);

		// send email
		mailSender.send(emailSend);

		return new ResponseEntity<>(new Message("registrasi berhasil, silahkan cek email untuk melakukan verifikasi"),
				HttpStatus.OK);
	}

	public ResponseEntity<?> verificationMbankingAccount(OtpRequest otp) throws ParseException {

		// multi threading
		Thread thread = new Thread() {
			public void run() {
				rabbitController.verification();
			}
		};
		thread.start();

		// convert object to string
		String message = new Gson().toJson(otp);
		
		// encrypt message
		String encrypt = EncriptMessage.encrypt(message, secretKey);
		
		// send data to back
		frontProducer.sendToBack(encrypt);

		// receive data form back
		frontConsumer.recieveFromBack();
		
		String response = frontConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(response, secretKey);

		if (decrypt.equals("FAIL01")) {
			return new ResponseEntity<>(new Message("something when wrong..!!!"), HttpStatus.NOT_FOUND);
		}

		if (decrypt.equals("FAIL02")) {
			return new ResponseEntity<>(new Message("verifikasi gagal"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new Message("verifilasi berhasil"), HttpStatus.OK);

	}

	public ResponseEntity<?> loginMbankingUsecase(LoginRequest login) {

		// multi threading
		Thread thread = new Thread() {
			public void run() {
				rabbitController.login();
			}
		};
		thread.start();

		// convert object to string
		String message = new Gson().toJson(login);
		 
		// encrypt message
		String encrypt = EncriptMessage.encrypt(message, secretKey);
		
		// send data to back
		frontProducer.sendToBack(encrypt);

		// receive data form back
		frontConsumer.recieveFromBack();
		String response = frontConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(response, secretKey);

		switch (decrypt) {
		case "NOT VERIFIED":
			return new ResponseEntity<>(new Message("akun anda belum aktif, silahkan verifikasi terlebih dahulu"),
					HttpStatus.BAD_REQUEST);
		case "BLOKIR":
			return new ResponseEntity<>(new Message("user anda di blokir, silahkan kunjungi call center terdekat"),
					HttpStatus.BAD_REQUEST);
		case "FAIL404":
			return new ResponseEntity<>(new Message("login gagal, mohon masukan usernameid dan mpin yang benar"),
					HttpStatus.BAD_REQUEST);
		default:
			break;
		}
		return new ResponseEntity<>(new Message("login berhasil"), HttpStatus.OK);

	}

	public ResponseEntity<?> saldoInformationUsecase(String usernameid, PinRequest pin) {

		// multi threading
		Thread thread = new Thread() {
			public void run() {
				rabbitController.saldoInformation();
			}
		};
		thread.start();

		BalanceInformationRequest request = new BalanceInformationRequest(usernameid, pin.getPin());

		// convert object to string
		String message = new Gson().toJson(request);

		// encrypt message
		String encrypt = EncriptMessage.encrypt(message, secretKey);
		
		// send data to back
		frontProducer.sendToBack(encrypt);

		// receive data form back
		frontConsumer.recieveFromBack();
		
		String response = frontConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(response, secretKey);

		switch (decrypt) {
		case "FAIL404":
			return new ResponseEntity<>(new Message("pin yang anda masukan salah"), HttpStatus.BAD_REQUEST);
		case "BLOKIR":
			return new ResponseEntity<>(new Message(
					"transaksi tidak dapat dilakukan, akun anda di blokir, silahkan hubungi call center terdekat"),
					HttpStatus.BAD_REQUEST);
		case "BLOCKED":
			return new ResponseEntity<>(new Message(
					" anda telah memasukan 5 kali kesalahan pin, untuk sementara akun anda kami blokir, silahkan hubungi call center terdekat"),
					HttpStatus.BAD_REQUEST);
		default:
			break;
		}

		BalanceInformationResponse responseBalance = new Gson().fromJson(decrypt, BalanceInformationResponse.class);
		return new ResponseEntity<>(responseBalance, HttpStatus.OK);
	}

	public ResponseEntity<?> voucherHandPhoneUsecase(String no_hp) throws ClientProtocolException, IOException {

		// multi threading
		Thread thread = new Thread() {
			public void run() {
				try {
					rabbitController.searchProvider();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();

		SearchProviderRequest request = new SearchProviderRequest(no_hp);

		// convert object to string
		String message = new Gson().toJson(request);

		// encrypt message
		String encrypt = EncriptMessage.encrypt(message, secretKey);
		
		// send data to back
		frontProducer.sendToBack(encrypt);

		// receive data form back
		frontConsumer.recieveFromBack();
		
		String response = frontConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(response, secretKey);

		if (decrypt.equals("FAIL404")) {
			return new ResponseEntity<>(new Message("frovider tidak tersedia"), HttpStatus.BAD_REQUEST);
		}

		Product[] product = new Gson().fromJson(decrypt, Product[].class);

		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	public ResponseEntity<?> voucherHandPhoneByType(String no_hp, String type)
			throws ClientProtocolException, IOException {

		// multi threading
		Thread thread = new Thread() {
			public void run() {
				try {
					rabbitController.searchProviderType();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();

		SearchProviderByTypeRequest request = new SearchProviderByTypeRequest(no_hp, type);
		// convert object to string
		String message = new Gson().toJson(request);

		// encrypt message
		String encrypt = EncriptMessage.encrypt(message, secretKey);
		
		// send data to back
		frontProducer.sendToBack(encrypt);

		// receive data form back
		frontConsumer.recieveFromBack();
		
		String response = frontConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(response, secretKey);

		if (decrypt.equals("FAIL404")) {
			return new ResponseEntity<>(new Message("frovider tidak tersedia"), HttpStatus.BAD_REQUEST);
		}

		Product[] product = new Gson().fromJson(decrypt, Product[].class);

		return new ResponseEntity<>(product, HttpStatus.OK);

	}

	public ResponseEntity<?> voucherHandPhoneCheckoutUsecase(String usernameid, String no_hp, String type, int price,
			@Valid PinRequest pin) throws ClientProtocolException, IOException {

		// multi threading
		Thread thread = new Thread() {
			public void run() {
				try {
					rabbitController.checkout();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		thread.start();
		
		String codeHp = no_hp.substring(0, 4);
		if (codeHp.equals("0857") || codeHp.equals("0816")) {
			merchant = new Merchant(1, "indosat");
		} else {
			merchant = new Merchant(2, "xl");
		}
		

		CheckoutRequest request = new CheckoutRequest(usernameid, pin, type, price, no_hp, merchant);
		
		// convert object to string
		String message = new Gson().toJson(request);

		// encrypt message
		String encrypt = EncriptMessage.encrypt(message, secretKey);
		
		// send data to back
		frontProducer.sendToBack(encrypt);

		// receive data form back
		frontConsumer.recieveFromBack();
		
		String response = frontConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(response, secretKey);

		switch (decrypt) {
		case "FAIL404":
			return new ResponseEntity<>(new Message("pin yang anda masukan salah"), HttpStatus.BAD_REQUEST);
		case "BLOKIR":
			return new ResponseEntity<>(new Message(
					"transaksi tidak dapat dilakukan, akun anda di blokir, silahkan hubungi call center terdekat"),
					HttpStatus.BAD_REQUEST);
		case "BLOCKED":
			return new ResponseEntity<>(new Message("anda telah memasukan 5 kali kesalahan pin, untuk sementara akun anda kami blokir, silahkan hubungi call center terdekat"),
					HttpStatus.BAD_REQUEST);
		case "FAIL404P":
			return new ResponseEntity<>(new Message("product tidak di temukan"), HttpStatus.BAD_REQUEST);
		case "FAILTRX":
			return new ResponseEntity<>(new Message("saldo anda tidak mencukupi"), HttpStatus.BAD_REQUEST);
		case "SUCCESS":
			return new ResponseEntity<>(new Message("transaksi berhasil"), HttpStatus.OK);
		default:
			break;
		}
		Gson gson = new Gson();
		Message resposeMessage = gson.fromJson(decrypt, Message.class);

		return new ResponseEntity<>(resposeMessage, HttpStatus.BAD_REQUEST);

	}

}
