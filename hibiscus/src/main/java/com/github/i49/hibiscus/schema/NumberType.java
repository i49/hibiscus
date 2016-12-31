package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;
import java.util.function.Predicate;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.ProblemDescriber;

/**
 * One of built-in types representing JSON number which has {@link TypeId#NUMBER} as a type identifier.
 * This type represents a signed decimal number with or without a fractional part. 
 * 
 * <p>An instance of this type can be created through {@link SchemaComponents#number()}.</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.schema.SchemaComponents.*;
 * NumberType t = number();
 * </code></pre></blockquote>
 * 
 * <h3>Restrictions on this type</h3>
 * <p>This type allows you to impose following restrictions on the value space.</p>
 * <ol>
 * <li>minInclusive</li>
 * <li>minExclusive</li>
 * <li>maxInclusive</li>
 * <li>maxExclusive</li>
 * <li>enumeration</li>
 * <li>assertion</li>
 * </ol>
 * 
 * <h4>1. minInclusive</h4>
 * <p><strong>minInclusive</strong> specifies the lower bound of the numeric range.
 * The bound value is included in the valid range.</p>
 * <blockquote><pre><code>number().minInclusive(42);</code></pre></blockquote>
 * 
 * <h4>2. minExclusive</h4>
 * <p><strong>minExclusive</strong> specifies the lower bound of the numeric range.
 * The bound value is excluded from the valid range.</p>
 * <blockquote><pre><code>number().minExclusive(42);</code></pre></blockquote>
 * 
 * <h4>3. maxInclusive</h4>
 * <p><strong>maxInclusive</strong> specifies the upper bound of the numeric range.
 * The bound value is included in the valid range.</p>
 * <blockquote><pre><code>number().maxInclusive(42);</code></pre></blockquote>
 * 
 * <h4>4. maxExclusive</h4>
 * <p><strong>maxExclusive</strong> specifies the upper bound of the numeric range.
 * The bound value is excluded from the valid range.</p>
 * <blockquote><pre><code>number().maxExclusive(42);</code></pre></blockquote>
 * 
 * <h4>5. enumeration</h4>
 * <p><strong>enumeration</strong> specifies the value space of this type as a set of distinct values.</p>
 * <blockquote><pre><code>number().enumeration(1, 2, 3);</code></pre></blockquote>
 * 
 * <h4>6. assertion</h4>
 * <p><strong>assertion</strong> allows you to make a arbitrary assertion on the values of this type.</p>
 * 
 * @see SchemaComponents
 */
public interface NumberType extends AtomicType {

	default TypeId getTypeId() {
		return TypeId.NUMBER;
	}
	
	/**
	 * Adds a {@link Facet} which restricts the value space of this type.
	 * @param facet the facet to be added. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if facet specified is {@code null}.
	 */
	NumberType facet(Facet<JsonNumber> facet);
	
	/**
	 * Specifies the inclusive lower bound of the value space for this type.
	 * @param value the inclusive lower bound value.
	 * @return this type.
	 */
	NumberType minInclusive(long value);

	/**
	 * Specifies the exclusive lower bound of the value space for this type.
	 * @param value the exclusive lower bound value.
	 * @return this type.
	 */
	NumberType minExclusive(long value);

	/**
	 * Specifies the inclusive lower bound of the value space for this type.
	 * @param value the inclusive lower bound value.
	 * @return this type.
	 */
	NumberType minInclusive(BigDecimal value);

	/**
	 * Specifies the exclusive lower bound of the value space for this type.
	 * @param value the exclusive lower bound value.
	 * @return this type.
	 */
	NumberType minExclusive(BigDecimal value);

	/**
	 * Specifies the inclusive upper bound of the value space for this type.
	 * @param value the inclusive upper bound value.
	 * @return this type.
	 */
	NumberType maxInclusive(long value);

	/**
	 * Specifies the exclusive upper bound of the value space for this type.
	 * @param value the exclusive upper bound value.
	 * @return this type.
	 */
	NumberType maxExclusive(long value);
	
	/**
	 * Specifies the inclusive upper bound of the value space for this type.
	 * @param value the inclusive upper bound value.
	 * @return this type.
	 */
	NumberType maxInclusive(BigDecimal value);
	
	/**
	 * Specifies the exclusive upper bound of the value space for this type.
	 * @param value the exclusive upper bound value.
	 * @return this type.
	 */
	NumberType maxExclusive(BigDecimal value);
	
	/**
	 * Specifies the value space of this type as empty.
	 * @return this type.
	 */
	NumberType enumeration();

	/**
	 * Specifies the value space of this type as a set of distinct values.
	 * @param values the values allowed for this type.
	 * @return this type.
	 */
	NumberType enumeration(long... values);
	
	/**
	 * Specifies the value space of this type as a set of distinct values.
	 * @param values the values allowed for this type. Each value cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if one of values specified is {@code null}.
	 */
	NumberType enumeration(BigDecimal... values);

	/**
	 * Makes a assertion on the values of this type.
	 * @param predicate the lambda expression that will return {@code true} if the assertion succeeded or {@code false} if failed.
	 * @param describer the object supplying the description of the problem to be reported when the assertion failed.
	 * @return this type.
	 * @exception SchemaException if any of specified parameters is {@code null}.
	 */
	NumberType assertion(Predicate<JsonNumber> predicate, ProblemDescriber<JsonNumber> describer);
}
