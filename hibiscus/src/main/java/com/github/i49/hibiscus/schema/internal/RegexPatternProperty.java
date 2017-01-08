package com.github.i49.hibiscus.schema.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.schema.JsonType;
import com.github.i49.hibiscus.schema.SchemaException;

/**
 * An object property which name is specified as a regular expression.
 */
public class RegexPatternProperty extends PatternProperty {

	private final Pattern pattern;
	
	/**
	 * Constructs this property.
	 * @param pattern the pattern of the name specified as a Java regular expression. Cannot be {@code null}.
	 * @param type the type of this property value. Cannot be {@code null}.
	 * @param moreTypes the other types allowed for this property value. Each type cannot be {@code null}.
	 * @exception SchemaException if pattern is {@code null} or
	 *                            if one of types has the same {@link TypeId} as others or {@code null}.
	 * @exception PatternSyntaxException if pattern's syntax is invalid.
	 */
	public RegexPatternProperty(String pattern, JsonType type, JsonType[] moreTypes) {
		super(type, moreTypes);
		if (pattern == null) {
			throw new SchemaException(Messages.REGULAR_EXPRESSION_IS_NULL());
		}
		this.pattern = Pattern.compile(pattern);
	}

	@Override
	public boolean matches(String name) {
		Matcher m = this.pattern.matcher(name);
		return m.matches();
	}
}
