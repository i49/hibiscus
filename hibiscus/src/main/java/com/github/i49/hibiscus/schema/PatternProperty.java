package com.github.i49.hibiscus.schema;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Property which name is specified as a regular expression.
 */
class PatternProperty implements Property {

	private final Pattern pattern;
	private final TypeSet typeSet;

	/**
	 * Constructs this property.
	 * @param pattern
	 * @param type the type of this property value.
	 * @param moreTypes the other types allowed for this property value.
	 * @return new property.
	 * @exception SchemaException if pattern is {@code null} or one of types is {@code null} or duplicated.
	 */
	public PatternProperty(String pattern, JsonType type, JsonType[] moreTypes) {
		if (pattern == null) {
			throw new SchemaException(Messages.PROPERTY_NAME_IS_NULL());
		}
		this.pattern = Pattern.compile(pattern);
		this.typeSet = TypeSet.of(type, moreTypes);
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TypeSet getTypeSet() {
		return typeSet;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	/**
	 * Matches given name to this property.
	 * @param name the name of property which may match this property.
	 * @return {@code true} if the name given matched this property or {@code false} it does not match.
	 */
	boolean matches(String name) {
		Matcher m = pattern.matcher(name);
		return m.matches();
	}
}
