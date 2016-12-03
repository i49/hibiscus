package com.github.i49.hibiscus.formats;

import com.github.i49.hibiscus.schema.StringType;

/**
 * Provides methods to create various kinds of formats.
 * 
 * <p>These formats can be arguments of {@link StringType#format} method.</p>
 */
public final class Formats {

	/**
	 * Returns the format which represents email addresses.
	 * @return the format for email addresses.
	 */
	public static StringFormat email() {
		return EmailFormat.getInstance();
	}
	
	/**
	 * Returns the format which represents hostnames.
	 * @return the format for hostnames.
	 */
	public static StringFormat hostname() {
		return HostnameFormat.getInstance();
	}

	/**
	 * Returns the format which represents IPv4 address.
	 * @return the format for IPv4 address.
	 */
	public static StringFormat ipv4() {
		return Inet4AddressFormat.getInstance();
	}

	/**
	 * Returns the format which represents IPv6 address.
	 * @return the format for IPv6 address.
	 */
	public static StringFormat ipv6() {
		return Inet6AddressFormat.getInstance();
	}
	
	/**
	 * Returns the format which represents URIs.
	 * @return the format for URIs.
	 */
	public static StringFormat uri() {
		return UriFormat.getInstance();
	}

	private Formats() {
	}
}
