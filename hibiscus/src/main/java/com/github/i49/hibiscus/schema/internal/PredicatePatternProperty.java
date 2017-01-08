package com.github.i49.hibiscus.schema.internal;

import java.util.function.Predicate;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.schema.JsonType;
import com.github.i49.hibiscus.schema.SchemaException;

/**
 * An object property which name is determined by a user-specified predicate.
 */
public class PredicatePatternProperty extends PatternProperty {

	private final Predicate<String> predicate;
	
	/**
	 * Constructs this property.
	 * @param predicate the predicate to determine whether the name of the property is acceptable or not.
	 *                  Cannot be {@code null}. 
	 * @param type the type of this property value. Cannot be {@code null}.
	 * @param moreTypes the other types allowed for this property value. Each type cannot be {@code null}.
	 * @exception SchemaException if predicate is {@code null} or
	 *                            if one of types has the same {@link TypeId} as others or {@code null}.
	 */
	public PredicatePatternProperty(Predicate<String> predicate, JsonType type, JsonType[] moreTypes) {
		super(type, moreTypes);
		if (predicate == null) {
			throw new SchemaException(Messages.PREDICATE_IS_NULL());
		}
		this.predicate = predicate;
	}

	@Override
	public boolean matches(String name) {
		return this.predicate.test(name);
	}
}
