package com.github.i49.hibiscus.formats;

import com.github.i49.hibiscus.schema.StringType;

/**
 * Provides methods to create various kinds of formats.
 * 
 * <p>These formats can be arguments of {@link StringType#format StringType.format} method
 * to declare the detailed format of the {@link StringType}.</p>
 */
public final class Formats {

	/**
	 * Returns the format which represents email addresses.
	 * @return the format for email addresses.
	 */
	public static StringFormat email() {
		return EmailFormat.INSTANCE;
	}
	
	/**
	 * Returns the format which represents Internet host names.
	 * @return the format for Internet host names.
	 */
	public static StringFormat hostname() {
		return HostnameFormat.INSTANCE;
	}

	/**
	 * Returns the format which represents Internet Protocol Version 4 address.
	 * @return the format for IPv4 address.
	 */
	public static StringFormat ipv4() {
		return Inet4AddressFormat.INSTANCE;
	}

	/**
	 * Returns the format which represents Internet Protocol Version 6 address.
	 * @return the format for IPv6 address.
	 */
	public static StringFormat ipv6() {
		return Inet6AddressFormat.INSTANCE;
	}
	
	/**
	 * Returns the format which represents any URIs including relative URIs.
	 * @return the format for URIs.
	 */
	public static StringFormat anyURI() {
		return URIFormat.INSTANCE;
	}

	/**
	 * Returns the format which represents absolute URIs.
	 * @return the format for URIs.
	 */
	public static StringFormat absoluteURI() {
		return AbsoluteURIFormat.INSTANCE;
	}
	
	private Formats() {
	}
}
