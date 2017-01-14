package com.github.i49.hibiscus.common;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JSON pointer which defines a string syntax for identifying a specific value within JSON document.
 * This object is immutable and cannot be modified one created.
 * 
 * @see <a href="https://tools.ietf.org/rfc/rfc6901.txt">RFC 6901: JavaScript Object Notation (JSON) Pointer</a>
 */
public class JsonPointer {

	private final List<Object> tokens;
	private static final JsonPointer POINTER_TO_DOCUMENT = new JsonPointer(Collections.emptyList());
			
	/**
	 * Creates a builder to build an instance of this class.
	 * @return created builder.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Returns the JSON pointer which represents the whole JSON document.
	 * @return the JSON pointer to the whole JSON document.
	 */
	public static JsonPointer toDocument() {
		return POINTER_TO_DOCUMENT;
	}
	
	/**
	 * Constructs this builder.
	 */
	private JsonPointer(List<Object> tokens) {
		this.tokens = tokens;
	}
	
	/**
	 * Returns the string representation of this JSON pointer.
	 * @return the string representation of the JSON pointer.
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (Object token: tokens) {
			b.append("/");
			b.append(escape(token.toString()));
		}
		return b.toString();
	}
	
	/**
	 * Returns the URI fragment identifier representation of this JSON pointer.
	 * @return the URI fragment identifier representation of this JSON pointer.
	 */
	public URI toURI() {
		try {
			return new URI(null, null, toString());
		} catch (URISyntaxException e) {
			return null;
		}
	}
	
	/**
	 * Escapes the reference token.
	 * @param token the reference token to be escaped.
	 * @return escaped token.
	 */
	private static String escape(String token) {
		return token.replaceAll("~", "~0").replaceAll("/", "~1");
	}
	
	/**
	 * A builder class to build an instance of JSON pointer.
	 */
	public static class Builder {
	
		private final List<Object> tokens = new ArrayList<>();
		
		private Builder() {
		}
		
		/**
		 * Appends the index of the item in JSON array to the JSON pointer to be built.
		 * @param index the index of the item.
		 * @return this builder.
		 */
		public Builder append(int index) {
			tokens.add(String.valueOf(index));
			return this;
		}

		/**
		 * Appends the name of the property in JSON object to the JSON pointer to be built.
		 * @param name the name of the property.
		 * @return this builder.
		 */
		public Builder append(String name) {
			tokens.add(name);
			return this;
		}
		
		/**
		 * Builds the JSON pointer.
		 * @return built JSON pointer.
		 */
		public JsonPointer build() {
			if (tokens.isEmpty()) {
				return JsonPointer.toDocument();
			} else {
				return new JsonPointer(tokens);
			}
		}
	}
}
