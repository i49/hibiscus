package com.github.i49.hibiscus.json;

import java.math.BigDecimal;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * Facade class which creates various kinds of JSON values.
 * 
 * This class is needed because standard API does not provide
 * any way to create JSON values directly, without JsonObjectBuilder
 * or JsonAraryBuilder.
 * Methods are defined to instantiate strings, numbers and booleans.
 */
public class JsonValues {

	/**
	 * Creates instance of JSON string which has specified string value. 
	 * @param value string value. 
	 * @return instance of JSON string.
	 */
	public static JsonString createString(String value) {
		return JsonStringImpl.valueOf(value);
	}
	
	/**
	 * Creates instance of JSON number which has specified integer value. 
	 * @param value integer value. 
	 * @return instance of JSON number.
	 */
	public static JsonNumber createNumber(int value) {
		return JsonIntNumberImpl.valueOf(value);
	}
	
	/**
	 * Creates instance of JSON number which has specified long value. 
	 * @param value long value. 
	 * @return instance of JSON number.
	 */
	public static JsonNumber createNumber(long value) {
		return JsonLongNumberImpl.valueOf(value);
	}
	
	/**
	 * Creates instance of JSON number which has specified decimal number value. 
	 * @param value decimal number value. 
	 * @return instance of JSON number.
	 */
	public static JsonNumber createNumber(BigDecimal value) {
		return JsonDecimalNumberImpl.valueOf(value);
	}
	
	/**
	 * Creates instance of JSON boolean which has specified boolean value. 
	 * @param value boolean value.
	 * @return instance of JSON boolean.
	 */
	public static JsonValue createBoolean(boolean value) {
		return value ? JsonValue.TRUE : JsonValue.FALSE;
	}
	
	private JsonValues() {
	}
}
