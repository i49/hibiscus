package com.github.i49.hibiscus.json;

import javax.json.JsonString;

class JsonStringImpl implements JsonString {
	
	public static final JsonString BLANK = new JsonStringImpl("");
	
	private final String value;
	
	public static JsonString valueOf(String value) {
		if (value == null) {
			throw new IllegalArgumentException();
		} else if (value.isEmpty()) {
			return BLANK;
		}
		return new JsonStringImpl(value);
	}

	private JsonStringImpl(String value) {
		this.value = value;
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
