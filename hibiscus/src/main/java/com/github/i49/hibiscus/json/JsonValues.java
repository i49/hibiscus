package com.github.i49.hibiscus.json;

import java.math.BigDecimal;

import javax.json.JsonNumber;
import javax.json.JsonString;

/**
 * Facade class which creates various kinds of JSON values.
 * 
 * This class is needed because standard API does not provide
 * any way to create JSON values directly, without JsonObjectBuilder
 * or JsonAraryBuilder.
 * Methods are defined to instantiate strings and numbers.
 * Other types of JSON values such as boolean and null 
 * are defined as static constants in standard JsonValue interface.
 */
public class JsonValues {

	/**
	 * Creates instance of JSON string which has specified string value. 
	 * @param value string value. 
	 * @return new instance of JSON string.
	 */
	public static JsonString createString(String value) {
		return JsonStringImpl.valueOf(value);
	}
	
	/**
	 * Creates instance of JSON number which has specified integer value. 
	 * @param value integer value. 
	 * @return new instance of JSON number.
	 */
	public static JsonNumber createNumber(int value) {
		return JsonIntNumberImpl.valueOf(value);
	}
	
	/**
	 * Creates instance of JSON number which has specified long value. 
	 * @param value long value. 
	 * @return new instance of JSON number.
	 */
	public static JsonNumber createNumber(long value) {
		return JsonLongNumberImpl.valueOf(value);
	}
	
	/**
	 * Creates instance of JSON number which has specified decimal number value. 
	 * @param value decimal number value. 
	 * @return new instance of JSON number.
	 */
	public static JsonNumber createNumber(BigDecimal value) {
		return JsonDecimalNumberImpl.valueOf(value);
	}
	
	private JsonValues() {
	}
}
