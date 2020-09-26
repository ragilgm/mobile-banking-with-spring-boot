package com.finalproject.ragil.finalproject.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class ConvertData {

	public LocalDate dateFormatISo (String date) {
		LocalDate localdate = LocalDate.parse(date);
		return localdate;
	}
	
	public String currencyIDR(int currency) {
		 DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
	        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
	        formatRp.setCurrencySymbol("Rp. ");
	        formatRp.setMonetaryDecimalSeparator(',');
	        formatRp.setGroupingSeparator('.');
	        kursIndonesia.setDecimalFormatSymbols(formatRp);
	        return kursIndonesia.format(currency);
	}	
}
