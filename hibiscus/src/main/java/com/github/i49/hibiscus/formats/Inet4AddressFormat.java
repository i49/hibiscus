package com.github.i49.hibiscus.formats;

import javax.json.JsonString;

import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * String format which represents IPv4 addresses.
 */
public class Inet4AddressFormat extends AbstractFormat<JsonString> implements StringFormat {

	/**
	 * The one and only instance of this format.
	 */
	private static final Inet4AddressFormat INSTANCE = new Inet4AddressFormat();
	
	/**
	 * Returns the Singleton instance of this format.
	 * @return the instance of this class.
	 */
	public static Inet4AddressFormat getInstance() {
		return INSTANCE;
	}

	private Inet4AddressFormat() {
	}

	@Override
	public String getName() {
		return "ipv4";
	}

	@Override
	public boolean matches(JsonString value) {
		return InetAddressValidator.getInstance().isValidInet4Address(value.getString());
	}
}
