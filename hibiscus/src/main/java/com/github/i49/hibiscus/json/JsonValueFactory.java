package com.github.i49.hibiscus.json;

import java.math.BigDecimal;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * Facade interface which creates various kinds of JSON values.
 * 
 * <p>This interface is necessary because the standard API does not provide
 * any means to create JSON values directly, without help of JsonObjectBuilder
 * or JsonAraryBuilder.</p>
 * <p>All methods of this interface are designed to instantiate JSON strings, numbers and booleans.</p>
 */
public interface JsonValueFactory {

	/**
	 * Creates instance of JSON string which has specified string value. 
	 * @param value the value of string type in Java. 
	 * @return created instance of JSON string.
	 */
	JsonString createString(String value);
	
	/**
	 * Creates instance of JSON number which has specified integer value. 
	 * @param value the value of integer type in Java. 
	 * @return created instance of JSON number.
	 */
	JsonNumber createNumber(int value);
	
	/**
	 * Creates instance of JSON number which has specified long value. 
	 * @param value the value of long type in Java. 
	 * @return created instance of JSON number.
	 */
	JsonNumber createNumber(long value);
	
	/**
	 * Creates instance of JSON number which has specified decimal number value. 
	 * @param value the value of BigDecimal type in Java. 
	 * @return created instance of JSON number.
	 */
	JsonNumber createNumber(BigDecimal value);
	
	/**
	 * Creates instance of JSON boolean which has specified boolean value. 
	 * @param value the value of boolean type in Java.
	 * @return created instance of JSON boolean.
	 */
	JsonValue createBoolean(boolean value);
}
