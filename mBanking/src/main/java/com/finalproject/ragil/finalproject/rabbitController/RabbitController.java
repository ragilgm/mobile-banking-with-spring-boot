package com.finalproject.ragil.finalproject.rabbitController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.finalproject.ragil.finalproject.config.SecurityUtility;
import com.finalproject.ragil.finalproject.enitity.Account;
import com.finalproject.ragil.finalproject.enitity.MbankingAccount;
import com.finalproject.ragil.finalproject.enitity.Transaction;
import com.finalproject.ragil.finalproject.enitity.request.BalanceInformationRequest;
import com.finalproject.ragil.finalproject.enitity.request.CheckoutRequest;
import com.finalproject.ragil.finalproject.enitity.request.LoginRequest;
import com.finalproject.ragil.finalproject.enitity.request.OtpRequest;
import com.finalproject.ragil.finalproject.enitity.request.RegisterRequest;
import com.finalproject.ragil.finalproject.enitity.request.SearchProviderByTypeRequest;
import com.finalproject.ragil.finalproject.enitity.request.SearchProviderRequest;
import com.finalproject.ragil.finalproject.enitity.response.BalanceInformationResponse;
import com.finalproject.ragil.finalproject.enitity.vendor.Merchant;
import com.finalproject.ragil.finalproject.enitity.vendor.Product;
import com.finalproject.ragil.finalproject.rabbitmq.back.BackConsumer;
import com.finalproject.ragil.finalproject.rabbitmq.back.BackProducer;
import com.finalproject.ragil.finalproject.rabbitmq.front.FrontConsumer;
import com.finalproject.ragil.finalproject.rabbitmq.front.FrontProducer;
import com.finalproject.ragil.finalproject.repository.impl.MbankingAccountRepository;
import com.finalproject.ragil.finalproject.service.MailConstructor;
import com.finalproject.ragil.finalproject.service.OtpGenerator;
import com.finalproject.ragil.finalproject.util.Counter;
import com.finalproject.ragil.finalproject.util.EncriptMessage;
import com.google.gson.Gson;

@Service
public class RabbitController {

	@Autowired
	BackConsumer backConsumer;
	@Autowired
	BackProducer backProducer;
	@Autowired
	FrontProducer frontProducer;
	@Autowired
	FrontConsumer frontConsumer;
	@Autowired
	MbankingAccountRepository mBankingRepo;
	@Autowired
	Counter counter;
	@Autowired
	MailConstructor email;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	OtpGenerator otpGenerator;
	@Autowired
	EncriptMessage encryptMessage;
	private final String secretKey = "rahasia";

	Merchant merchant;

	MbankingAccount newAccount;

	public void Register() {

		// back end rabbit
		// handle message from rest
		backConsumer.recieveFromFront();
		String frontRequest = backConsumer.getPesan();

		String decrypt = EncriptMessage.decrypt(frontRequest, secretKey);
		
		RegisterRequest register = new Gson().fromJson(decrypt, RegisterRequest.class);

		MbankingAccount request = new MbankingAccount();
		request.setAccountnumber(register.getAccountnumber());
		request.setEmail(register.getEmail());
		request.setEktp(register.getEktp());
		request.setUsernameid(register.getUsernameid());
		String encryptedPassword1 = SecurityUtility.passwordEncoder().encode(register.getMpin());
		request.setMpin(encryptedPassword1);
		String encryptedPassword2 = SecurityUtility.passwordEncoder().encode(register.getPin());
		request.setPin(encryptedPassword2);

		Account result = mBankingRepo.checkAccount(request);
		System.out.println(result);

		if (result == null) {
			String encrypt = EncriptMessage.encrypt("FAIL01", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}
		if (mBankingRepo.checkMobileBankingAccount(request) != null) {
			String encrypt = EncriptMessage.encrypt("DUPLICATED", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		} else {
			MbankingAccount insertData = new MbankingAccount(result.getId(), result.getAccountnumber(), result.getPin(), result.getEmail(),
					result.getDateofbirth(), result.getEktp(), request.getUsernameid(), request.getMpin(),
					"not verified");

			newAccount = request;

			int registrasiMbanking = mBankingRepo.registerMbankingAccount(insertData);
			if (registrasiMbanking == 0) {
				String encrypt = EncriptMessage.encrypt("FAIL02", secretKey);
				backProducer.sendToFront(encrypt);
			} else {
				String encrypt = EncriptMessage.encrypt("SUCCESS", secretKey);
				backProducer.sendToFront(encrypt);
			}
		}

	}

	public void verification() {
		// back end rabbit
		// handle message from rest
		backConsumer.recieveFromFront();
		String frontRequest = backConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(frontRequest, secretKey);
		System.out.println(decrypt);
		OtpRequest otp = new Gson().fromJson(decrypt, OtpRequest.class);

		// validate otp
		Boolean isValid = OtpGenerator.validateOTP(newAccount.getUsernameid(), otp.getOtp());

		if (!isValid) {
			String encrypt = EncriptMessage.encrypt("FAIL01", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}

		newAccount.setStatus("aktif");
		int updateMbankingAccount = mBankingRepo.updateMbankingAccount(newAccount);

		if (updateMbankingAccount == 0) {
			String encrypt = EncriptMessage.encrypt("FAIL02", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		} else {
			String encrypt = EncriptMessage.encrypt("SUCCESS", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}

	}

	public void login() {
		// back end rabbit
		// handle message from rest
		backConsumer.recieveFromFront();
		String frontRequest = backConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(frontRequest, secretKey);
		
		LoginRequest request = new Gson().fromJson(decrypt, LoginRequest.class);
		
		
		MbankingAccount result = mBankingRepo.findByUsername(request.getUsernameid());
		System.out.println(result);
		if(result==null) {
			String encrypt = EncriptMessage.encrypt("FAIL404", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}
	
		
		BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
		if (!passwordEncoder.matches(request.getMpin(), result.getMpin())) {
			String encrypt = EncriptMessage.encrypt("FAIL404", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		} 

		if (result != null) {
			switch (result.getStatus()) {
			case "not verified":
				String encrypt = EncriptMessage.encrypt("NOT VERIFIED", secretKey);
				backProducer.sendToFront(encrypt);
				return;
			case "blokir":
				 encrypt = EncriptMessage.encrypt("BLOKIR", secretKey);
				backProducer.sendToFront(encrypt);
				return;
			case "aktif":
				 encrypt = EncriptMessage.encrypt("SUCCESS", secretKey);
				backProducer.sendToFront(encrypt);
				return;
			default:
				break;
			}
		}

	}

	public void saldoInformation() {

		// back end rabbit
		// handle message from rest
		backConsumer.recieveFromFront();
		String frontRequest = backConsumer.getPesan();

		String decrypt = EncriptMessage.decrypt(frontRequest, secretKey);
		
		BalanceInformationRequest request = new Gson().fromJson(decrypt, BalanceInformationRequest.class);
		
		MbankingAccount result = mBankingRepo.findByUsername(request.getUsernameid());
		System.out.println(result);
		BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
		if (!passwordEncoder.matches(request.getPin(), result.getPin())) {
			counter.setCounter(1);
			if (counter.getCounter() == 5) {
				MbankingAccount account = new MbankingAccount();
				account.setStatus("blokir");
				account.setUsernameid(request.getUsernameid());
				int updateMbankingAccount = mBankingRepo.updateMbankingAccount(account);
				if (updateMbankingAccount == 1) {
					counter.reset();
					 String encrypt = EncriptMessage.encrypt("BLOCKED", secretKey);
					backProducer.sendToFront(encrypt);
					return;
				}
			}
			 String encrypt = EncriptMessage.encrypt("FAIL404", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}

		if (result.getStatus().equals("blokir")) {
			 String encrypt = EncriptMessage.encrypt("BLOKIR", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}

		if (result != null && result.getStatus().equals("aktif")) {
			Account account = mBankingRepo.accountInformation(result);

			BalanceInformationResponse balance = new BalanceInformationResponse(new Date(), result.getAccountnumber(),
					account.getFirstName() + " " + account.getLastName(), account.getBalance());
			counter.reset();
			// convert object to string
			String message = new Gson().toJson(balance);
			 String encrypt = EncriptMessage.encrypt(message, secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}

	}

	public void searchProvider() throws ClientProtocolException, IOException {
		// back end rabbit
		// handle message from rest
		backConsumer.recieveFromFront();
		String frontRequest = backConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(frontRequest, secretKey);

		SearchProviderRequest request = new Gson().fromJson(decrypt, SearchProviderRequest.class);

		CloseableHttpClient httpCLient = HttpClients.createDefault();

		HttpGet httpRequest = null;
		String codeHp = request.getPhoneNumber().substring(0, 4);

		if (codeHp.equals("0857") || codeHp.equals("0816")) {
			merchant = new Merchant(1, "indosat");
			httpRequest = new HttpGet("http://localhost:8085/products");

		} else if (codeHp.equals("0877") || codeHp.equals("0878")) {
			merchant = new Merchant(2, "xl");
			httpRequest = new HttpGet("http://localhost:8086/products");

		} else {
			String encrypt = EncriptMessage.encrypt("FAIL404", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}
		// add request headers
		httpRequest.addHeader("Content-Type", "application/json");

		String data = null;

		CloseableHttpResponse response = httpCLient.execute(httpRequest);

		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// return it as a String
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line = "";
				while ((line = rd.readLine()) != null) {
					data = line;
					System.out.println(line);
				}
			}
		} finally {
			response.close();
			httpCLient.close();
		}
		Gson gson = new Gson();
		Product[] productArray = gson.fromJson(data, Product[].class);
		// convert object to string
		String message = new Gson().toJson(productArray);
		String encrypt = EncriptMessage.encrypt(message, secretKey);
		backProducer.sendToFront(encrypt);

	}

	public void searchProviderType() throws ClientProtocolException, IOException {
		// back end rabbit
		// handle message from rest
		backConsumer.recieveFromFront();
		String frontRequest = backConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(frontRequest, secretKey);

		SearchProviderByTypeRequest request = new Gson().fromJson(decrypt, SearchProviderByTypeRequest.class);

		CloseableHttpClient httpCLient = HttpClients.createDefault();
		HttpGet httpRequest;
		String codeHp = request.getPhoneMumber().substring(0, 4);
		if (codeHp.equals("0857") || codeHp.equals("0816")) {
			merchant = new Merchant(1, "indosat");
			httpRequest = new HttpGet("http://localhost:8085/products/" + request.getType());

		} else if (codeHp.equals("0877") || codeHp.equals("0878")) {
			merchant = new Merchant(2, "xl");
			httpRequest = new HttpGet("http://localhost:8086/products/" + request.getType());
		} else {
			String encrypt = EncriptMessage.encrypt("FAIL404", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}

		// add request headers
		httpRequest.addHeader("Content-Type", "application/json");

		String data = null;

		CloseableHttpResponse response = httpCLient.execute(httpRequest);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// return it as a String
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line = "";
				while ((line = rd.readLine()) != null) {
					data = line;
				}
			}

		} finally {
			response.close();
			httpCLient.close();
		}

		Gson gson = new Gson();
		Product[] productArray = gson.fromJson(data, Product[].class);
		// convert object to string
		String message = new Gson().toJson(productArray);
		String encrypt = EncriptMessage.encrypt(message, secretKey);
		backProducer.sendToFront(encrypt);

	}

	public void checkout() throws ClientProtocolException, IOException {

		// back end rabbit
		// handle message from rest
		backConsumer.recieveFromFront();
		String frontRequest = backConsumer.getPesan();
		
		String decrypt = EncriptMessage.decrypt(frontRequest, secretKey);

		CheckoutRequest request = new Gson().fromJson(decrypt, CheckoutRequest.class);
		
		MbankingAccount result = mBankingRepo.findByUsername(request.getUsernameid());
		System.out.println(result);
		BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
		
		if (!passwordEncoder.matches(request.getPin().getPin(), result.getPin())) {
			counter.setCounter(1);
			if (counter.getCounter() == 5) {
				MbankingAccount acc = new MbankingAccount();
				acc.setStatus("blokir");
				acc.setUsernameid(request.getUsernameid());
				int updateMbankingAccount = mBankingRepo.updateMbankingAccount(acc);
				if (updateMbankingAccount == 1) {
					counter.reset();
					String encrypt = EncriptMessage.encrypt("BLOCKED", secretKey);
					backProducer.sendToFront(encrypt);
				}
				return;
			}
			System.out.println(counter.getCounter());
			String encrypt = EncriptMessage.encrypt("FAIL404", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}

		if (result.getStatus().equals("blokir")) {
			String encrypt = EncriptMessage.encrypt("BLOKIR", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}
		
		CloseableHttpClient httpCLient = HttpClients.createDefault();
		HttpGet httpRequest = null;
		System.out.println(request.getMerchant().getMerhantname());
		if(request.getMerchant().getMerhantname().equals("indosat")) {
		httpRequest = new HttpGet("http://localhost:8085/products/"+request.getType()+"/"+request.getPrice());
		} 
		if(request.getMerchant().getMerhantname().equals("xl")){
			httpRequest = new HttpGet("http://localhost:8086/products/"+request.getType()+"/"+request.getPrice());
		}
		// add request headers
				httpRequest.addHeader("Content-Type", "application/json");

				String data = null;

				CloseableHttpResponse response = httpCLient.execute(httpRequest);
				try {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						// return it as a String
						BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
						String line = "";
						while ((line = rd.readLine()) != null) {
							data = line;
						}
					}

				} finally {
					response.close();
					httpCLient.close();
				}

				Gson gson = new Gson();
				Product product = gson.fromJson(data, Product.class);

		if (product == null) {
			String encrypt = EncriptMessage.encrypt("FAIL404P", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}

		Account account = mBankingRepo.accountInformation(result);
		
		if (account.getBalance() < product.getPrice()) {
			String encrypt = EncriptMessage.encrypt("FAILTRX", secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}
		
		String payload = "{" + "\"no_hp\": \"" + request.getNo_hp() + "\"," + "\"type\": \""
				+ product.getType() + "\"," + "\"id\": " + product.getId() + "}";

		StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost requestClient = null;
		
		if (request.getMerchant().getMerhantname().equals("indosat")) {
			requestClient = new HttpPost("http://localhost:8085/transaction");
		}

		if (request.getMerchant().getMerhantname().equals("xl")) {
			requestClient = new HttpPost("http://localhost:8086/transaction");
		}

		requestClient.setEntity(entity);

		HttpResponse res = httpClient.execute(requestClient);
		System.out.println(res.getStatusLine().getStatusCode());
		if (res.getStatusLine().getStatusCode() != 200) {
			 data = null;

			if (entity != null) {
				// return it as a String
				BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
				String line = "";
				while ((line = rd.readLine()) != null) {
					data = line;
				}
			}
			String encrypt = EncriptMessage.encrypt(data, secretKey);
			backProducer.sendToFront(encrypt);
			return;
		}

		Transaction transaction = new Transaction();
		transaction.setMerchantid(request.getMerchant().getMerchantId());
		transaction.setMerchantname(request.getMerchant().getMerhantname());
		transaction.setAccountid(account.getId());
		transaction.setAmmount(request.getPrice());
		transaction.setTax(1000);
		transaction.setDate(new Date());

		int transactionRequest = mBankingRepo.transaction(transaction);
		if (transactionRequest != 0) {
			int balance = account.getBalance() - transaction.getAmmount() - transaction.getTax();
			int updateBalance = mBankingRepo.updateBalance(balance, account.getAccountnumber());
			if (updateBalance != 0) {

				SimpleMailMessage emailSend = email.transactionMail(result.getEmail(), transaction);
				// send email
				mailSender.send(emailSend);
				String encrypt = EncriptMessage.encrypt("SUCCESS", secretKey);
				backProducer.sendToFront(encrypt);
			}
		}

	}

}
