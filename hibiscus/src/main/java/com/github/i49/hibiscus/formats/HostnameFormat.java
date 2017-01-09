package com.github.i49.hibiscus.formats;

import org.apache.commons.validator.routines.DomainValidator;

/**
 * <strong>hostname</strong> format which represents Internet host name
 * as defined in RFC 1034 and RFC 1123.
 * <p>
 * An instance of this format can be obtained by {@link Formats#hostname()} method.
 * </p>
 * 
 *  @see <a href="https://www.ietf.org/rfc/rfc1034.txt">RFC 1034: DOMAIN NAMES - CONCEPTS AND FACILITIES</a>
 *  @see <a href="https://www.ietf.org/rfc/rfc1123.txt">RFC 1123: Requirements for Internet Hosts -- Application and Support</a>
 */
public class HostnameFormat extends StringFormat {

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
	public boolean test(String value) {
		return DomainValidator.getInstance().isValid(value);
	}
}
