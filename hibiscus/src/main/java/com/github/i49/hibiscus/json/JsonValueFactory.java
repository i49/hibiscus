package com.github.i49.hibiscus.json;

import java.math.BigDecimal;

import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * A facade interface which creates various kinds of JSON values.
 * 
 * <p>This interface is necessary because the standard API does not provide
 * any means to create JSON values directly, 
 * without help of {@link JsonObjectBuilder} or {@link JsonArrayBuilder}.</p>
 */
public interface JsonValueFactory {

	/**
	 * Creates an instance of JSON string which has specified string value. 
	 * @param value the value of string type in Java. 
	 * @return created instance of JSON string.
	 */
	JsonString createString(String value);
	
	/**
	 * Creates an instance of JSON number which has specified integer value. 
	 * @param value the value of integer type in Java. 
	 * @return created instance of JSON number.
	 */
	JsonNumber createNumber(int value);
	
	/**
	 * Creates an instance of JSON number which has specified long value. 
	 * @param value the value of long type in Java. 
	 * @return created instance of JSON number.
	 */
	JsonNumber createNumber(long value);
	
	/**
	 * Creates an instance of JSON number which has specified decimal number value. 
	 * @param value the value of BigDecimal type in Java. 
	 * @return created instance of JSON number.
	 */
	JsonNumber createNumber(BigDecimal value);
	
	/**
	 * Creates an instance of JSON boolean which has specified boolean value. 
	 * @param value the value of boolean type in Java, {@code true} or {@code false}.
	 * @return created instance of JSON boolean.
	 */
	JsonValue createBoolean(boolean value);
}
