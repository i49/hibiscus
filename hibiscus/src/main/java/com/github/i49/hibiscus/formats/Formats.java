package com.github.i49.hibiscus.formats;

/**
 * A facade class to provide methods to create various kinds of formats 
 * implementing {@link Format} interface.
 * 
 * <p>
 * All formats currently supported are shown in the table below.
 * </p>
 * <table border="1" cellpadding="4" style="border-collapse: collapse;">
 * <caption>The list of formats</caption>
 * <tr>
 * <th>No.</th>
 * <th>Format Name</th>
 * <th>Description</th>
 * <th>Method to Create</th>
 * <th>Implemented By</th>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>datetime</td>
 * <td>date and time format as defined in RFC 3339.</td>
 * <td>{@link #datetime()}</td>
 * <td>{@link DateTimeFormat}</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>email</td>
 * <td>email address format as defined in RFC 822.</td>
 * <td>{@link #email()}</td>
 * <td>{@link EmailFormat}</td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>hostname</td>
 * <td>Internet domain name as defined by RFC 1034 and RFC 1123.<br>
 * This does not include IPv4 or IPv6 address.</td>
 * <td>{@link #hostname()}</td>
 * <td>{@link HostnameFormat}</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>ipv4</td>
 * <td>Internet Protocol Version 4 address.</td>
 * <td>{@link #ipv4()}</td>
 * <td>{@link Inet4AddressFormat}</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>ipv6</td>
 * <td>Internet Protocol Version 6 address.</td>
 * <td>{@link #ipv6()}</td>
 * <td>{@link Inet6AddressFormat}</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>host</td>
 * <td>Internet host which may be {@code ipv4}, {@code ipv6}, or {@code hostname}.</td>
 * <td>{@link #host()}</td>
 * <td>{@link HostFormat}</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td>hostport</td>
 * <td>Internet host optionally followed by a port number.</td>
 * <td>{@link #hostport()}</td>
 * <td>{@link HostFormat}</td>
 * </tr>
 * <tr>
 * <td>8</td>
 * <td>anyURI</td>
 * <td>any URI including relative URI.</td>
 * <td>{@link #anyURI()}</td>
 * <td>{@link URIFormat}</td>
 * </tr>
 * <tr>
 * <td>9</td>
 * <td>absoluteURI</td>
 * <td>absolute URI only.</td>
 * <td>{@link #absoluteURI()}</td>
 * <td>{@link AbsoluteURIFormat}</td>
 * </tr>
 * </table>
 *
 * <p>For example, the following code shows how to obtain datetime format by means of this class.</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.formats.Formats;
 * datetime();
 * </code></pre></blockquote>
 *
 * @see Format
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
