package com.github.i49.hibiscus.formats;

import javax.json.JsonString;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * String format which represents email address.
 */
public class EmailFormat extends AbstractFormat<JsonString> implements StringFormat {

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
	public boolean matches(JsonString jsonValue) {
		return EmailValidator.getInstance().isValid(jsonValue.getString());
	}
}
