package com.github.i49.hibiscus.schema;

import java.util.function.Predicate;

import javax.json.JsonArray;

import com.github.i49.hibiscus.facets.LengthFacet;
import com.github.i49.hibiscus.facets.MaxLengthFacet;
import com.github.i49.hibiscus.facets.MinLengthFacet;
import com.github.i49.hibiscus.facets.UniqueItemFacet;
import com.github.i49.hibiscus.problems.ArrayLengthProblem;
import com.github.i49.hibiscus.problems.ArrayTooLongProblem;
import com.github.i49.hibiscus.problems.ArrayTooShortProblem;
import com.github.i49.hibiscus.problems.DescriptionSupplier;

/**
 * Implementation of {@code ArrayType}.
 */
class ArrayTypeImpl extends AbstractRestrictableType<JsonArray, ArrayType> implements ArrayType {

	private TypeSet typeSet = TypeSet.empty();
	
	/**
	 * Constructs this type.
	 */
	ArrayTypeImpl() {
	}

	@Override
	public ArrayType items(JsonType... types) {
		this.typeSet = TypeSet.of(types);
		return this;
	}

	@Override
	public TypeSet getItemTypes() {
		return typeSet;
	}

	@Override
	public ArrayType length(int length) {
		verifyLength(length);
		addFacet(new LengthFacet<JsonArray>(length, ArrayTypeImpl::getLength, ArrayLengthProblem::new));
		return this;
	}
	
	@Override
	public ArrayType minLength(int length) {
		verifyLength(length);
		addFacet(new MinLengthFacet<JsonArray>(length, ArrayTypeImpl::getLength, ArrayTooShortProblem::new));
		return this;
	}

	@Override
	public ArrayType maxLength(int length) {
		verifyLength(length);
		addFacet(new MaxLengthFacet<JsonArray>(length, ArrayTypeImpl::getLength, ArrayTooLongProblem::new));
		return this;
	}
	
	@Override
	public ArrayType unique() {
		addFacet(UniqueItemFacet.INSTANCE);
		return this;
	}
	
	@Override
	public ArrayType assertion(Predicate<JsonArray> predicate, DescriptionSupplier<JsonArray> description) {
		return super.assertion(predicate, description);
	}

	/**
	 * Returns the number of elements in array.
	 * @param value the array value.
	 * @return length of array.
	 */
	private static int getLength(JsonArray value) {
		return value.size();
	}
	
	/**
	 * Verifies value specified as length of array.
	 * @param length the length specified for arrays.
	 */
	private static void verifyLength(int length) {
		if (length < 0) {
			throw new SchemaException(Messages.ARRAY_SIZE_IS_NEGATIVE(length));
		}
	}
}
