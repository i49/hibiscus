package com.github.i49.hibiscus.formats;

import javax.json.JsonString;

import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * String format which represents Internet Protocol Version 6 address.
 */
public class Inet6AddressFormat extends AbstractFormat<JsonString> implements StringFormat {

	/**
	 * The Singleton instance of this format.
	 */
	public static final Inet6AddressFormat INSTANCE = new Inet6AddressFormat();

	Inet6AddressFormat() {}

	@Override
	public String getName() {
		return "ipv6";
	}

	@Override
	public boolean matches(JsonString value) {
		return InetAddressValidator.getInstance().isValidInet6Address(value.getString());
	}
}
