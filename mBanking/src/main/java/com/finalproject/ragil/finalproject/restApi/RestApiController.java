package com.finalproject.ragil.finalproject.restApi;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.finalproject.ragil.finalproject.enitity.request.LoginRequest;
import com.finalproject.ragil.finalproject.enitity.request.OtpRequest;
import com.finalproject.ragil.finalproject.enitity.request.PinRequest;
import com.finalproject.ragil.finalproject.enitity.request.RegisterRequest;
import com.finalproject.ragil.finalproject.usecase.MBanking_Usecase;
import com.finalproject.ragil.finalproject.util.Message;

@RestController
public class RestApiController {

	@Autowired
	MBanking_Usecase usecase;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Message home() {
		return new Message("welcome to mybanking");
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> mBankingRegistration(HttpServletRequest request,
			@Valid @RequestBody RegisterRequest register) throws ParseException {
		String username = null, sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					username = cookie.getValue();

				}
				if (cookie.getName().equals("JSESSIONID")) {
					sessionID = cookie.getValue();

				}
			}
		}
		if (sessionID != null || username != null) {
			return new ResponseEntity<>(new Message("anda sedang loggin sebagail " + username), HttpStatus.BAD_REQUEST);
		}

		return usecase.mBankingRegistrationUsecase(register);
	}

	@RequestMapping(value = "/verification", method = RequestMethod.POST)
	public ResponseEntity<?> verificationNasabah(HttpServletRequest request, @Valid @RequestBody OtpRequest otp)
			throws ParseException {
		return usecase.verificationMbankingAccount(otp);
	}

	@RequestMapping(value = "/log-out", method = RequestMethod.POST)
	public ResponseEntity<?> logoutMbanking(HttpServletRequest req, HttpServletResponse res) throws ParseException {
		req.getSession().invalidate();
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("username")) {
				cookie.setValue(null);
				cookie.setMaxAge(0);
				res.addCookie(cookie);
			}
			if (cookie.getName().equals("JSESSIONID")) {
				cookie.setValue(null);
				cookie.setMaxAge(0);
				res.addCookie(cookie);
			}
		}
		return new ResponseEntity<>(new Message("logged out"), HttpStatus.OK);
	}

	@RequestMapping(value = "/log-in", method = RequestMethod.POST)
	public ResponseEntity<?> LoginMbanking(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody LoginRequest login) throws ParseException {
		String username = null, sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					username = cookie.getValue();

				}
				if (cookie.getName().equals("JSESSIONID")) {
					sessionID = cookie.getValue();

				}
			}
		}
		if (sessionID != null || username != null) {
			return new ResponseEntity<>(new Message("anda sedang login"), HttpStatus.BAD_REQUEST);
		}

		ResponseEntity<?> responseEntity = usecase.loginMbankingUsecase(login);
		int res = responseEntity.getStatusCodeValue();
		if (res != 200) {
			return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.BAD_REQUEST);

		}
		request.getSession().invalidate();
		HttpSession newSession = request.getSession(true);
		newSession.setMaxInactiveInterval(300);
		Cookie cUsername = new Cookie("username", login.getUsernameid());
		response.addCookie(cUsername);

		return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);

	}

	@RequestMapping(value = "/balance", method = RequestMethod.POST)
	public ResponseEntity<?> BalanceInformation(HttpServletRequest request, @Valid @RequestBody PinRequest pin)
			throws ParseException {
		String username = null, sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					username = cookie.getValue();

				}
				if (cookie.getName().equals("JSESSIONID")) {
					sessionID = cookie.getValue();

				}
			}
		}
		if (sessionID == null || username == null) {
			return new ResponseEntity<>(new Message("something when wrong..!!"), HttpStatus.BAD_REQUEST);
		}
		return usecase.saldoInformationUsecase(username, pin);
	}

	@RequestMapping(value = "/voucher/handphone", method = RequestMethod.GET)
	public ResponseEntity<?> voucherHandPhone(HttpServletRequest request, @RequestParam String no_hp)
			throws ClientProtocolException, IOException {
		String username = null, sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					username = cookie.getValue();

				}
				if (cookie.getName().equals("JSESSIONID")) {
					sessionID = cookie.getValue();

				}
			}
		}
		if (sessionID == null || username == null) {
			return new ResponseEntity<>(new Message("something when wrong..!!"), HttpStatus.BAD_REQUEST);
		}
		return usecase.voucherHandPhoneUsecase(no_hp);
	}

	@RequestMapping(value = "/voucher/handphone/{type}", method = RequestMethod.GET)
	public ResponseEntity<?> voucherHandPhone(HttpServletRequest request, @PathVariable("type") String type,
			@RequestParam String no_hp) throws ClientProtocolException, IOException {
		String username = null, sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					username = cookie.getValue();

				}
				if (cookie.getName().equals("JSESSIONID")) {
					sessionID = cookie.getValue();

				}
			}
		}
		if (sessionID == null || username == null) {
			return new ResponseEntity<>(new Message("something when wrong..!!"), HttpStatus.BAD_REQUEST);
		}
		return usecase.voucherHandPhoneByType(no_hp, type);
	}

	@RequestMapping(value = "/checkout/voucher/handphone/{type}", method = RequestMethod.POST)
	public ResponseEntity<?> voucherHandPhoneCheckout(HttpServletRequest request, @PathVariable("type") String type,
			@RequestParam String no_hp, @RequestParam int price, @Valid @RequestBody PinRequest pin)
			throws ClientProtocolException, IOException {
		String username = null, sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					username = cookie.getValue();

				}
				if (cookie.getName().equals("JSESSIONID")) {
					sessionID = cookie.getValue();

				}
			}
		}
		if (sessionID == null || username == null) {
			return new ResponseEntity<>(new Message("something when wrong..!!"), HttpStatus.BAD_REQUEST);
		}
		return usecase.voucherHandPhoneCheckoutUsecase(username, no_hp, type, price, pin);
	}

}
