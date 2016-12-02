package com.github.i49.hibiscus.formats;

import javax.json.JsonString;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * String format representing email.
 */
public class EmailFormat extends AbstractFormat<JsonString> implements StringFormat {

	/**
	 * The one and only instance of this format.
	 */
	private static final EmailFormat INSTANCE = new EmailFormat();
	
	private static final EmailValidator validator = EmailValidator.getInstance();

	/**
	 * Returns the Singleton instance of this format.
	 * @return the instance of this class.
	 */
	public static EmailFormat getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Constructs this format.
	 */
	private EmailFormat() {
	}

	@Override
	public String getName() {
		return "email";
	}

	@Override
	public boolean matches(JsonString value) {
		return validator.isValid(value.getString());
	}
}
