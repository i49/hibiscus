package com.github.i49.hibiscus.formats;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * <strong>email</strong> format which represents email address as defined in RFC 822.
 * <p>
 * An instance of this format can be obtained by {@link Formats#email()} method.
 * </p>
 * 
 * @see <a href="https://www.ietf.org/rfc/rfc822.txt">RFC 822: STANDARD FOR THE FORMAT OF ARPA INTERNET TEXT MESSAGES</a>
 */
public class EmailFormat extends StringFormat {

	/**
	 * The Singleton instance of this format.
	 */
	public static final EmailFormat INSTANCE = new EmailFormat();
	
	private EmailFormat() {
	}

	@Override
	public String getName() {
		return "email";
	}

	@Override
	public boolean test(String value) {
		return EmailValidator.getInstance().isValid(value);
	}
}
