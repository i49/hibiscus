package com.github.i49.hibiscus.schema;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.DescriptionSupplier;

import java.util.function.Predicate;

/**
 * JSON type for boolean value.
 */
public interface BooleanType extends AtomicType {
	
	/**
	 * Specifies values allowed for this type.
	 * @param values the values allowed.
	 * @return this type.
	 */
	BooleanType enumeration(boolean... values);

	/**
	 * Specifies assertion on this type.
	 * @param predicate the lambda expression that will return true if the assertion succeeded or false if failed.
	 * @param description the object to supply a description to be reported when the assertion failed.
	 * @return this type.
	 */
	BooleanType assertion(Predicate<JsonValue> predicate, DescriptionSupplier<JsonValue> description);
}
