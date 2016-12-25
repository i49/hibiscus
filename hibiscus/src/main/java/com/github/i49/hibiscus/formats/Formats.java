package com.github.i49.hibiscus.formats;

/**
 * A facade class to provide methods to create various kinds of formats 
 * implementing {@link Format} interface.
 * 
 * <p>For example, the following code shows how to obtain datetime format by means of this class.</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.formats.Formats;
 * datetime();
 * </code></pre></blockquote>
 * <p>
 * All formats available are shown in <a href="package-summary.html#list-of-formats">List of formats</a>.
 * </p>
 * 
 * @see Format
 * @see <a href="package-summary.html#list-of-formats">List of formats</a>
 */
public final class Formats {

	/**
	 * Returns the <strong>datetime</strong> format which represents date and time as defined in RFC 3339.
	 * @return the format representing date and time.
	 * @see DateTimeFormat
	 */
	public static StringFormat datetime() {
		return DateTimeFormat.INSTANCE;
	}
	
	/**
	 * Returns the <strong>email</strong> format which represents email address as defined in RFC 822.
	 * @return the format representing email address.
	 * @see EmailFormat
	 */
	public static StringFormat email() {
		return EmailFormat.INSTANCE;
	}
	
	/**
	 * Returns the <strong>hostname</strong> format which represents Internet host name as defined in RFC 1034 and RFC 1123.
	 * @return the format representing Internet host name.
	 * @see HostnameFormat
	 */
	public static StringFormat hostname() {
		return HostnameFormat.INSTANCE;
	}

	/**
	 * Returns the <strong>ipv4</strong> format which represents Internet Protocol Version 4 address.
	 * @return the format representing IPv4 address.
	 * @see Inet4AddressFormat
	 */
	public static StringFormat ipv4() {
		return Inet4AddressFormat.INSTANCE;
	}

	/**
	 * Returns the <strong>ipv6</strong> format which represents Internet Protocol Version 6 address.
	 * @return the format representing IPv6 address.
	 * @see Inet6AddressFormat
	 */
	public static StringFormat ipv6() {
		return Inet6AddressFormat.INSTANCE;
	}
	
	/**
	 * Returns the <strong>host</strong> format which represents Internet host as defined by RFC 2396, amended by RFC 2732.
	 * @return the format representing Internet host.
	 * @see HostFormat
	 */
	public static StringFormat host() {
		return HostFormat.INSTANCE;
	}

	/**
	 * Returns the <strong>hostport</strong> format which represents Internet host followed by port number.
	 * @return the format representing Internet host and port.
	 * @see HostFormat
	 */
	public static StringFormat hostport() {
		return HostFormat.INSTANCE_WITH_PORT;
	}
	
	/**
	 * Returns the <strong>anyURI</strong> format which represents URI including relative URI.
	 * @return the format representing any URI.
	 * @see URIFormat
	 */
	public static StringFormat anyURI() {
		return URIFormat.INSTANCE;
	}

	/**
	 * Returns the <strong>absouluteURI</strong> format which represents absolute URI.
	 * @return the format representing absolute URI.
	 * @see AbsoluteURIFormat
	 */
	public static StringFormat absoluteURI() {
		return AbsoluteURIFormat.INSTANCE;
	}
	
	private Formats() {
	}
}
