package com.github.i49.hibiscus.formats;

import javax.json.JsonString;

import org.apache.commons.validator.routines.DomainValidator;

/**
 * String format which represents hostname.
 */
public class HostnameFormat extends AbstractFormat<JsonString> implements StringFormat {

	/**
	 * The one and only instance of this format.
	 */
	private static final HostnameFormat INSTANCE = new HostnameFormat();
	
	/**
	 * Returns the Singleton instance of this format.
	 * @return the instance of this class.
	 */
	public static HostnameFormat getInstance() {
		return INSTANCE;
	}

	/**
	 * Constructs this format.
	 */
	private HostnameFormat() {
	}
	
	@Override
	public String getName() {
		return "hostname";
	}

	@Override
	public boolean matches(JsonString value) {
		return DomainValidator.getInstance().isValid(value.getString());
	}
}
