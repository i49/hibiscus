package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;
import java.util.function.Predicate;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.ProblemDescriber;

/**
 * One of built-in types representing JSON integer which has {@link TypeId#INTEGER} as a type identifier.
 * This type is derived from {@link NumberType} and represents a signed decimal number without a fractional part. 
 * 
 * <p>An instance of this type can be created through {@link SchemaComponents#integer()}.</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.schema.SchemaComponents.*;
 * IntegerType t = integer();
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
 * <blockquote><pre><code>integer().minInclusive(42);</code></pre></blockquote>
 * 
 * <h4>2. minExclusive</h4>
 * <p><strong>minExclusive</strong> specifies the lower bound of the numeric range.
 * The bound value is excluded from the valid range.</p>
 * <blockquote><pre><code>integer().minExclusive(42);</code></pre></blockquote>
 * 
 * <h4>3. maxInclusive</h4>
 * <p><strong>maxInclusive</strong> specifies the upper bound of the numeric range.
 * The bound value is included in the valid range.</p>
 * <blockquote><pre><code>integer().maxInclusive(42);</code></pre></blockquote>
 * 
 * <h4>4. maxExclusive</h4>
 * <p><strong>maxExclusive</strong> specifies the upper bound of the numeric range.
 * The bound value is excluded from the valid range.</p>
 * <blockquote><pre><code>integer().maxExclusive(42);</code></pre></blockquote>
 * 
 * <h4>5. enumeration</h4>
 * <p><strong>enumeration</strong> specifies the value space of this type as a set of distinct values.</p>
 * <blockquote><pre><code>integer().enumeration(1, 2, 3);</code></pre></blockquote>
 * 
 * <h4>6. assertion</h4>
 * <p><strong>assertion</strong> allows you to make a arbitrary assertion on the values of this type.</p>
 * 
 * @see SchemaComponents
 */
public interface IntegerType extends NumberType {

	default TypeId getTypeId() {
		return TypeId.INTEGER;
	}
	
	IntegerType facet(Facet<JsonNumber> facet);

	IntegerType minInclusive(long value);

	IntegerType minExclusive(long value);

	IntegerType minInclusive(BigDecimal value);

	IntegerType minExclusive(BigDecimal value);

	IntegerType maxInclusive(long value);

	IntegerType maxExclusive(long value);

	IntegerType maxInclusive(BigDecimal value);
	
	IntegerType maxExclusive(BigDecimal value);
	
	IntegerType enumeration();
	
	IntegerType enumeration(long... values);

	IntegerType enumeration(BigDecimal... values);

	IntegerType assertion(Predicate<JsonNumber> predicate, ProblemDescriber<JsonNumber> describer);
}
