package com.github.i49.hibiscus.formats;

import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * <strong>ipv6</strong> format which represents Internet Protocol Version 6 address.
 * <p>
 * An instance of this format can be obtained by {@link Formats#ipv6()} method.
 * </p>
 */
public class Inet6AddressFormat extends StringFormat {

	/**
	 * The Singleton instance of this format.
	 */
	public static final Inet6AddressFormat INSTANCE = new Inet6AddressFormat();

	private Inet6AddressFormat() {
	}

	@Override
	public String getName() {
		return "ipv6";
	}

	@Override
	public boolean test(String value) {
		return InetAddressValidator.getInstance().isValidInet6Address(value);
	}
}
