package com.github.i49.hibiscus.schema;

import static com.github.i49.hibiscus.schema.Enumerations.valueSet;

import javax.json.JsonString;

import com.github.i49.hibiscus.facets.EnumerationFacet;
import com.github.i49.hibiscus.facets.LengthFacet;
import com.github.i49.hibiscus.facets.MaxLengthFacet;
import com.github.i49.hibiscus.facets.MinLengthFacet;
import com.github.i49.hibiscus.facets.PatternFacet;
import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.problems.StringLengthProblem;
import com.github.i49.hibiscus.problems.StringTooLongProblem;
import com.github.i49.hibiscus.problems.StringTooShortProblem;

/**
 * Implementation of {@code StringType}.
 */
class StringTypeImpl extends AbstractRestrictableType<JsonString, StringType> implements StringType {

	StringTypeImpl() {
	}

	@Override
	public StringType length(int length) {
		verifyLength(length);
		addFacet(new LengthFacet<JsonString>(length, StringTypeImpl::getLength, StringLengthProblem::new));
		return this;
	}
	
	@Override
	public StringType minLength(int length) {
		verifyLength(length);
		addFacet(new MinLengthFacet<JsonString>(length, StringTypeImpl::getLength, StringTooShortProblem::new));
		return this;
	}
	
	@Override
	public StringType maxLength(int length) {
		verifyLength(length);
		addFacet(new MaxLengthFacet<JsonString>(length, StringTypeImpl::getLength, StringTooLongProblem::new));
		return this;
	}
	
	@Override
	public StringType enumeration(String... values) {
		addFacet(EnumerationFacet.of(valueSet(JsonValues::createString, values)));
		return this;
	}
	
	@Override
	public StringType pattern(String regex) {
		if (regex == null) {
			throw new SchemaException(Messages.METHOD_PARAMETER_IS_NULL("pattern", "regex"));
		}
		addFacet(new PatternFacet(regex));
		return this;
	}
	
	/**
	 * Returns the number of characters in string.
	 * @param value the string value.
	 * @return length of string.
	 */
	private static int getLength(JsonString value) {
		return value.getString().length();
	}

	/**
	 * Verifies value specified as length of string.
	 * @param length the length specified for strings.
	 */
	private static void verifyLength(int length) {
		if (length < 0) {
			throw new SchemaException(Messages.STRING_LENGTH_IS_NEGATIVE(length));
		}
	}
}
