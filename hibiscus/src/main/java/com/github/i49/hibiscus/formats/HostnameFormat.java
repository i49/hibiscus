package com.github.i49.hibiscus.formats;

import javax.json.JsonString;

import org.apache.commons.validator.routines.DomainValidator;

/**
 * String format which represents Internet host names
 * as defined in RFC 1034 and RFC 1123. 
 */
public class HostnameFormat extends AbstractFormat<JsonString> implements StringFormat {

	/**
	 * The Singleton instance of this format.
	 */
	public static final HostnameFormat INSTANCE = new HostnameFormat();
	
	private HostnameFormat() {
	}
	
	@Override
	public String getName() {
		return "hostname";
	}

	@Override
	public boolean matches(JsonString jsonValue) {
		return DomainValidator.getInstance().isValid(jsonValue.getString());
	}
}
