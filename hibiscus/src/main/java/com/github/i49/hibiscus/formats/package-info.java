/**
 * Provides various kinds of <i>formats</i> that allow you to specify the known format for
 * values of types defined in JSON schema.
 * 
 * <h2 id="introducing-formats">1. Introducing Formats</h2>
 * 
 * <p>
 * A <strong>format</strong> is one of restrictions on the value spaces of the types.
 * Types in schema can select a format from predefined ones such as email address or IPv4 address.
 * That makes it unnecessary to write a complex regular expression representing email address.
 * Most formats defined here are described by authoritative parties and considered as standard.
 * </p>
 * <p>
 * This package provides various kinds of format implementations, 
 * and all these classes implement common {@link com.github.i49.hibiscus.formats.Format Format} interface.
 * </p>
 * <p>
 * These formats can be obtained by static methods of {@link com.github.i49.hibiscus.formats.Formats Formats} class.
 * Each format can be applied to the built-in types with help of {@link com.github.i49.hibiscus.facets.FormatFacet FormatFacet},
 * which is one of <i>facets</i> provided by {@link com.github.i49.hibiscus.facets} package.
 * All formats currently available can be applied only to {@code string()} type.
 * </p>
 * 
 * <h2 id="list-of-formats">2. List of Formats</h2>
 * 
 * <p>
 * The table shown below lists all formats currently supported.
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
 * <td>{@link com.github.i49.hibiscus.formats.Formats#datetime() Formats.datetime()}</td>
 * <td>{@link com.github.i49.hibiscus.formats.DateTimeFormat DateTimeFormat}</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>email</td>
 * <td>email address format as defined in RFC 822.</td>
 * <td>{@link com.github.i49.hibiscus.formats.Formats#email() Formats.email()}</td>
 * <td>{@link com.github.i49.hibiscus.formats.EmailFormat EmailFormat}</td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>hostname</td>
 * <td>Internet domain name as defined by RFC 1034 and RFC 1123.<br>
 * This does not include IPv4 or IPv6 address.</td>
 * <td>{@link com.github.i49.hibiscus.formats.Formats#hostname() Formats.hostname()}</td>
 * <td>{@link com.github.i49.hibiscus.formats.HostnameFormat HostnameFormat}</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>ipv4</td>
 * <td>Internet Protocol Version 4 address.</td>
 * <td>{@link com.github.i49.hibiscus.formats.Formats#ipv4() Formats.ipv4()}</td>
 * <td>{@link com.github.i49.hibiscus.formats.Inet4AddressFormat Inet4AddressFormat}</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>ipv6</td>
 * <td>Internet Protocol Version 6 address.</td>
 * <td>{@link com.github.i49.hibiscus.formats.Formats#ipv6() Formats.ipv6()}</td>
 * <td>{@link com.github.i49.hibiscus.formats.Inet6AddressFormat Inet6AddressFormat}</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>host</td>
 * <td>Internet host which may be {@code ipv4}, {@code ipv6}, or {@code hostname}.</td>
 * <td>{@link com.github.i49.hibiscus.formats.Formats#host() Formats.host()}</td>
 * <td>{@link com.github.i49.hibiscus.formats.HostFormat HostFormat}</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td>hostport</td>
 * <td>Internet host optionally followed by a port number.</td>
 * <td>{@link com.github.i49.hibiscus.formats.Formats#hostport() Formats.hostport()}</td>
 * <td>{@link com.github.i49.hibiscus.formats.HostFormat HostFormat}</td>
 * </tr>
 * <tr>
 * <td>8</td>
 * <td>anyURI</td>
 * <td>any URI including relative URI.</td>
 * <td>{@link com.github.i49.hibiscus.formats.Formats#anyURI() Formats.anyURI()}</td>
 * <td>{@link com.github.i49.hibiscus.formats.URIFormat URIFormat}</td>
 * </tr>
 * <tr>
 * <td>9</td>
 * <td>absoluteURI</td>
 * <td>absolute URI only.</td>
 * <td>{@link com.github.i49.hibiscus.formats.Formats#absoluteURI() Formats.absoluteURI()}</td>
 * <td>{@link com.github.i49.hibiscus.formats.AbsoluteURIFormat AbsoluteURIFormat}</td>
 * </tr>
 * </table>
 */
package com.github.i49.hibiscus.formats;
