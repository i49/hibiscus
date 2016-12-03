package com.github.i49.hibiscus.formats;

import javax.json.JsonString;

import org.apache.commons.validator.routines.DomainValidator;

/**
 * String format which represents Internet host names.
 */
public class HostnameFormat extends AbstractFormat<JsonString> implements StringFormat {

	/**
	 * The Singleton instance of this format.
	 */
	public static final HostnameFormat INSTANCE = new HostnameFormat();
	
	HostnameFormat() {}
	
	@Override
	public String getName() {
		return "hostname";
	}

	@Override
	public boolean matches(JsonString value) {
		return DomainValidator.getInstance().isValid(value.getString());
	}
}
