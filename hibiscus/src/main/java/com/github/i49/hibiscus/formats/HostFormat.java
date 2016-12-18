package com.github.i49.hibiscus.formats;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonString;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * String format which represents Internet host and port.
 * Internet host may be either IPv4 address, IPv6 address or Internet domain name.
 * 
 * <p>Valid format for host and port is defined by RFC 2396: Uniform Resource Identifiers (URI): Generic Syntax,
 * amended by RFC 2732: Format for Literal IPv6 Addresses in URLs.</p>
 *
 * @see <a href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396: Uniform Resource Identifiers (URI): Generic Syntax</a>
 * @see <a href="http://www.ietf.org/rfc/rfc2732.txt">RFC 2732: Format for Literal IPv6 Addresses in URLs</a>
 */
public class HostFormat implements StringFormat {
	
	/**
	 * The Singleton instance of this format representing Internet host, not followed by port.
	 */
	public static final HostFormat INSTANCE = new HostFormat(false);

	/**
	 * The Singleton instance of this format representing Internet host and port.
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
		return hasPort ? "hostport" : "port";
	}

	@Override
	public boolean matches(JsonString jsonValue) {
		String value = jsonValue.getString();
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
