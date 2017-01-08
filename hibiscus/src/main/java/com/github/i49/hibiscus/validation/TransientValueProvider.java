package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;

import javax.json.JsonNumber;
import javax.json.JsonString;

import com.github.i49.hibiscus.json.WritableJsonDecimalNumber;
import com.github.i49.hibiscus.json.WritableJsonIntNumber;
import com.github.i49.hibiscus.json.WritableJsonLongNumber;
import com.github.i49.hibiscus.json.WritableJsonString;

/**
 * A provider that provides transient JSON values.
 */
class TransientValueProvider {
	
	private final WritableJsonIntNumber intValue = new WritableJsonIntNumber();
	private final WritableJsonLongNumber longValue = new WritableJsonLongNumber();
	private final WritableJsonDecimalNumber decimalValue = new WritableJsonDecimalNumber();
	private final WritableJsonString stringValue = new WritableJsonString();

	/**
	 * Returns an instance of JSON number which is only valid before the next invocation of this method.
	 * @param value the value of integer type in Java. 
	 * @return the instance of JSON number.
	 */
	JsonNumber getNumber(int value) {
		return intValue.assign(value);
	}
	
	/**
	 * Returns an instance of JSON number which is only valid before the next invocation of this method.
	 * @param value the value of long type in Java. 
	 * @return the instance of JSON number.
	 */
	JsonNumber getNumber(long value) {
		return longValue.assign(value);
	}

	/**
	 * Returns an instance of JSON number which is only valid before the next invocation of this method.
	 * @param value the value of BigDecimal type in Java. 
	 * @return the instance of JSON number.
	 */
	JsonNumber getNumber(BigDecimal value) {
		return decimalValue.assign(value);
	}

	/**
	 * Returns an instance of JSON string which is only valid before the next invocation of this method.
	 * @param value the value of string type in Java. 
	 * @return the instance of JSON string.
	 */
	JsonString getString(String value) {
		return stringValue.assign(value);
	}
}
