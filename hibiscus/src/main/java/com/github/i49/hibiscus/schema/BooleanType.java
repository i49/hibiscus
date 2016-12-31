package com.github.i49.hibiscus.schema;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.ProblemDescriber;

import java.util.function.Predicate;

/**
 * One of built-in types representing JSON boolean which has {@link TypeId#BOOLEAN} as a type identifier.
 * 
 * <p>An instance of this type can be created through {@link SchemaComponents#bool()}.</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.schema.SchemaComponents.*;
 * BooleanType t = bool();
 * </code></pre></blockquote>
 * 
 * <h3>Restrictions on this type</h3>
 * <p>This type allows you to impose following restrictions on the value space.</p>
 * <ol>
 * <li>enumeration</li>
 * <li>assertion</li>
 * </ol>
 *
 * <h4>1. enumeration</h4>
 * <p><strong>enumeration</strong> specifies the value space of this type as a set of distinct values.</p>
 * <blockquote><pre><code>bool().enumeration(true);</code></pre></blockquote>
 *
 * <h4>2. assertion</h4>
 * <p><strong>assertion</strong> allows you to make a arbitrary assertion on the values of this type.</p>
 *
 * @see SchemaComponents
 */
public interface BooleanType extends AtomicType {
	
	default TypeId getTypeId() {
		return TypeId.BOOLEAN;
	}

	/**
	 * Adds a {@link Facet} which restricts the value space of this type.
	 * @param facet the facet to be added. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if facet specified is {@code null}.
	 */
	BooleanType facet(Facet<JsonValue> facet);
	
	/**
	 * Specifies the value space of this type as a set of distinct values.
	 * @param values the values allowed for this type.
	 * @return this type.
	 */
	BooleanType enumeration(boolean... values);

	/**
	 * Makes a assertion on the values of this type.
	 * @param predicate the lambda expression that will return {@code true} if the assertion succeeded or {@code false} if failed.
	 * @param describer the object supplying the description of the problem to be reported when the assertion failed.
	 * @return this type.
	 * @exception SchemaException if any of specified parameters is {@code null}.
	 */
	BooleanType assertion(Predicate<JsonValue> predicate, ProblemDescriber<JsonValue> describer);
}
