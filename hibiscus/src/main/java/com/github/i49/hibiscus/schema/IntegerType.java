package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;
import java.util.function.Predicate;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.DescriptionSupplier;

/**
 * JSON type for numeric value without a fraction or exponent part.
 */
public interface IntegerType extends NumberType {

	default TypeId getTypeId() {
		return TypeId.INTEGER;
	}

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

	IntegerType assertion(Predicate<JsonNumber> predicate, DescriptionSupplier<JsonNumber> description);
}
