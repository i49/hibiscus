package com.github.i49.hibiscus.schema;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.ProblemDescriber;

import java.util.function.Predicate;

/**
 * JSON type for boolean value.
 * 
 * <h3>Boolean type constraints</h3>
 * <p>Boolean type can have following facets constraining its value space.</p>
 * <ul>
 * <li>enumeration</li>
 * </ul>
 *
 * <h4>enumeration</h4>
 * <p>{@link #enumeration enumeration} specifies a distinct set of valid values for the type.
 * <blockquote><pre>bool().enumeration(true);</pre></blockquote>
 */
public interface BooleanType extends AtomicType {
	
	default TypeId getTypeId() {
		return TypeId.BOOLEAN;
	}

	/**
	 * Adds a facet to this type.
	 * @param facet the facet to be added. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if facet specified is {@code null}.
	 */
	BooleanType facet(Facet<JsonValue> facet);
	
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
	BooleanType assertion(Predicate<JsonValue> predicate, ProblemDescriber<JsonValue> description);
}
