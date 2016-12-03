package com.github.i49.hibiscus.formats;

import javax.json.JsonString;

import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * String format which represents Internet Protocol Version 4 address.
 */
public class Inet4AddressFormat extends AbstractFormat<JsonString> implements StringFormat {

	/**
	 * The Singleton instance of this format.
	 */
	public static final Inet4AddressFormat INSTANCE = new Inet4AddressFormat();
	
	Inet4AddressFormat() {}

	@Override
	public String getName() {
		return "ipv4";
	}

	@Override
	public boolean matches(JsonString value) {
		return InetAddressValidator.getInstance().isValidInet4Address(value.getString());
	}
}
