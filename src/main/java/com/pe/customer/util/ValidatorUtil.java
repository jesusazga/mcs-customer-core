package com.pe.customer.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidatorUtil {

	private static final String DATE_PATTERN = "yyyy-MM-dd";
	
	private ValidatorUtil() {
    }
	
	public static boolean validateDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate parsedDate = LocalDate.parse(date, formatter);

        String[] dateParts = date.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);

        if (parsedDate.getYear() != year || parsedDate.getMonthValue() != month || parsedDate.getDayOfMonth() != day) {
            return false;
        }
        return true;
    }
	
	public static boolean validIfIsAfter(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		LocalDate birthDateLocal = LocalDate.parse(date, formatter);
		
		if (birthDateLocal.isAfter(LocalDate.now())) {
            return true;
        }
		return false;
	}
}
