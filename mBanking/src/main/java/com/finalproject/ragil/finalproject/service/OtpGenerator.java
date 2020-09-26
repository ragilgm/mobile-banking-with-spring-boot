package com.finalproject.ragil.finalproject.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpGenerator {

	public static int currentOTP;

	private static final Integer EXPIRE_MIN = 60*24;
	private static LoadingCache<String, Integer> otpCache;

	public OtpGenerator() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(String s) throws Exception {
						return 0;
					}
				});
	}

	public Integer generateOTP(String key) {
		Random random = new Random();
		int OTP = 100000 + random.nextInt(900000);
		otpCache.put(key, OTP);
		currentOTP = getOPTByKey(key);
		return OTP;
	}

	public static Integer getOPTByKey(String key) {
		try {
			return otpCache.get(key);
		} catch (ExecutionException e) {
			return -1;
		}
	}

	public static void clearOTPFromCache(String key) {
		currentOTP = 0;
		otpCache.invalidate(key);
	}

	public static Boolean validateOTP(String key, Integer otpNumber) {
		// get OTP from cache
		Integer cacheOTP = getOPTByKey(key);
		if (cacheOTP.equals(otpNumber)) {
			clearOTPFromCache(key);
			return true;
		}
		return false;
	}

	public static Boolean checkValidOtp(String key) {
		// get OTP from cache
		Integer cacheOTP = getOPTByKey(key);
		if (cacheOTP.equals(currentOTP)) {
			return true;
		}
		return false;
	}
	
	public static Boolean checkValidOtpPayment(int otp) {
		// get OTP from cache
		Integer cacheOTP = getOPTByKey("admin");
		if (cacheOTP.equals(otp)) {
			return true;
		}
		return false;
	}
}
