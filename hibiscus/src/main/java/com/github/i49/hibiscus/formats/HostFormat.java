package com.github.i49.hibiscus.formats;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * <strong>host</strong> and <strong>hostport</strong> formats which represent Internet host and port.
 * <p>
 * Internet host may be either IPv4 address, IPv6 address or Internet domain name.
 * Valid format for host and port is defined by RFC 2396: Uniform Resource Identifiers (URI): Generic Syntax,
 * amended by RFC 2732: Format for Literal IPv6 Addresses in URLs.
 * </p>
 * <p>
 * An instance of this format can be obtained by {@link Formats#host()} method.
 * </p>
 *
 * @see <a href="https://www.ietf.org/rfc/rfc2396.txt">RFC 2396: Uniform Resource Identifiers (URI): Generic Syntax</a>
 * @see <a href="https://www.ietf.org/rfc/rfc2732.txt">RFC 2732: Format for Literal IPv6 Addresses in URLs</a>
 */
public class HostFormat extends StringFormat {
	
	/**
	 * The Singleton instance of this format representing Internet host, not followed by port number.
	 */
	public static final HostFormat INSTANCE = new HostFormat(false);

	/**
	 * The Singleton instance of this format representing Internet host and port number.
	 */
	public static final HostFormat INSTANCE_WITH_PORT = new HostFormat(true);

	private final boolean hasPort;

	private static final Pattern INET6_AND_PORT = Pattern.compile("\\[([^\\]]*)\\](:\\d+)?");
	private static final Pattern HOST_AND_PORT = Pattern.compile("([^:]*)(:\\d+)?");
	
	private HostFormat(boolean hasPort) {
		this.hasPort = hasPort;
	}

	@Override
	public String getName() {
		return hasPort ? "hostport" : "host";
	}

	@Override
	public boolean test(String value) {
		Matcher m = INET6_AND_PORT.matcher(value);
		if (m.matches()) {
			if (!matchesInet6Address(m.group(1))) {
				return false;
			}
		} else {
			m = HOST_AND_PORT.matcher(value);
			if (m.matches()) {
				String hostPart = m.group(1);
				if (!matchesInet4Address(hostPart) && !matchesHostname(hostPart)) {
					return false;
				}
			} else {
				return false;
			}
		}
		String portPart = m.group(2);
		return matchesPort(portPart);
	}
	
	private static boolean matchesInet4Address(String value) {
		return InetAddressValidator.getInstance().isValidInet4Address(value);
	}

	private static boolean matchesInet6Address(String value) {
		return InetAddressValidator.getInstance().isValidInet6Address(value);
	}
	
	private static boolean matchesHostname(String value) {
		return DomainValidator.getInstance().isValid(value);
	}
	
	private boolean matchesPort(String value) {
		return (hasPort || value == null);
	}
}
