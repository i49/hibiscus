package com.github.i49.hibiscus.json;

import javax.json.JsonString;

/**
 * A writable string value which implements {@link JsonString}.
 */
public class WritableJsonString implements JsonString {
	
	private String value;
	
	/**
	 * Constructs this JSON value with default value.
	 */
	public WritableJsonString() {
		this.value = "";
	}
	
	/**
	 * Constructs this JSON value.
	 * @param value the value to be assigned.
	 * @exception IllegalArgumentException if the value is {@code null}.
	 */
	public WritableJsonString(String value) {
		if (value == null) {
			throw new IllegalArgumentException("value is null.");
		}
		this.value = value;
	}
	
	/**
	 * Assigns a value to this JSON value.
	 * @param value the value to be assigned.
	 * @return this JSON value.
	 * @exception IllegalArgumentException if the value is {@code null}.
	 */
	public WritableJsonString assign(String value) {
		if (value == null) {
			throw new IllegalArgumentException("value is null.");
		}
		this.value = value;
		return this;
	}

	@Override
	public ValueType getValueType() {
		return ValueType.STRING;
	}

	@Override
	public CharSequence getChars() {
		return value;
	}

	@Override
	public String getString() {
		return value;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof JsonString)) {
			return false;
		}
		return getString().equals(((JsonString)obj).getString());
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append('"');
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (0x20 <= ch && ch != '"' && ch != '\\') {
				b.append(ch);
			} else {
				switch (ch) {
				case '"':
				case '\\':
					b.append('\\').append(ch);
					break;
				case '\b':
					b.append('\\').append('b');
					break;
				case '\f':
					b.append('\\').append('f');
					break;
				case '\n':
					b.append('\\').append('n');
					break;
				case '\r':
					b.append('\\').append('r');
					break;
				case '\t':
					b.append('\\').append('t');
					break;
				default:
					b.append("\\u");
					b.append(Integer.toHexString(0x10000 | ch).substring(1));
					break;
				}
			}
		}
		b.append('"');
		return b.toString();
	}
}
