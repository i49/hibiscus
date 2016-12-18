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
	 * Returns the format which represents date and time defined in RFC 3339.
	 * @return the format representing date and time.
	 */
	public static StringFormat datetime() {
		return DateTimeFormat.INSTANCE;
	}
	
	/**
	 * Returns the format which represents email addresses.
	 * @return the format representing email addresses.
	 */
	public static StringFormat email() {
		return EmailFormat.INSTANCE;
	}
	
	/**
	 * Returns the format which represents Internet host names.
	 * @return the format representing Internet host names.
	 */
	public static StringFormat hostname() {
		return HostnameFormat.INSTANCE;
	}

	/**
	 * Returns the format which represents Internet Protocol Version 4 address.
	 * @return the format representing IPv4 address.
	 */
	public static StringFormat ipv4() {
		return Inet4AddressFormat.INSTANCE;
	}

	/**
	 * Returns the format which represents Internet Protocol Version 6 address.
	 * @return the format representing IPv6 address.
	 */
	public static StringFormat ipv6() {
		return Inet6AddressFormat.INSTANCE;
	}
	
	/**
	 * Returns the format which represents Internet host
	 * as defined by RFC 2396: Uniform Resource Identifiers (URI): Generic Syntax, 
	 * amended by RFC 2732: Format for Literal IPv6 Addresses in URLs. 
	 * @return the format representing Internet host.
	 * @see <a href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396: Uniform Resource Identifiers (URI): Generic Syntax</a>
	 * @see <a href="http://www.ietf.org/rfc/rfc2732.txt">RFC 2732: Format for Literal IPv6 Addresses in URLs</a>
	 */
	public static StringFormat host() {
		return HostFormat.INSTANCE;
	}

	/**
	 * Returns the format which represents Internet host and port.
	 * @return the format representing Internet host and port.
	 */
	public static StringFormat hostport() {
		return HostFormat.INSTANCE_WITH_PORT;
	}
	
	/**
	 * Returns the format which represents any URIs including relative URIs.
	 * @return the format representing any URIs.
	 */
	public static StringFormat anyURI() {
		return URIFormat.INSTANCE;
	}

	/**
	 * Returns the format which represents absolute URIs.
	 * @return the format representing absolute URIs.
	 */
	public static StringFormat absoluteURI() {
		return AbsoluteURIFormat.INSTANCE;
	}
	
	private Formats() {
	}
}
