package com.pe.customer.util;

public class SetValueUtil {

	public static double convertTwoDecimal(double value) {
		return Math.round(value * 100.0) / 100.0;
	}
}
