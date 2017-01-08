package com.github.i49.hibiscus.schema.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.schema.JsonType;
import com.github.i49.hibiscus.schema.Property;
import com.github.i49.hibiscus.schema.SchemaException;
import com.github.i49.hibiscus.schema.TypeSet;

/**
 * An object property which name is specified as a regular expression.
 */
public class PatternProperty implements Property {

	private final Pattern pattern;
	private final TypeSet typeSet;

	/**
	 * Constructs this property.
	 * @param pattern the pattern of the name specified as a Java regular expression. Cannot be {@code null}.
	 * @param type the type of this property value. Cannot be {@code null}.
	 * @param moreTypes the other types allowed for this property value. Each type cannot be {@code null}.
	 * @exception SchemaException if pattern is {@code null} or
	 *                            if one of types has the same {@link TypeId} as others or {@code null}.
	 * @exception PatternSyntaxException if pattern's syntax is invalid.
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
	 * Matches the given name to this property.
	 * @param name the name of the property which may match this property.
	 * @return {@code true} if the name given matched this property, {@code false} otherwise.
	 */
	boolean matches(String name) {
		Matcher m = this.pattern.matcher(name);
		return m.matches();
	}
}
