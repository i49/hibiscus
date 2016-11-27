package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonValue;

/**
 * Problem that assertion on specific type was failed. 
 * 
 * @param V the type of the value in JSON document.
 */
public class AssertionFailureProblem<V extends JsonValue> extends ValueProblem<V> {

	private final DescriptionSupplier<V> descriptionSupplier;
	
	/**
	 * Constructs this problem.
	 * @param value the actual value that is the cause of this problem.
	 * @param descriptionSupplier supplying the description of this problem. 
	 */
	public AssertionFailureProblem(V value, DescriptionSupplier<V> descriptionSupplier) {
		super(value);
		this.descriptionSupplier = descriptionSupplier;
	}

	@Override
	protected String buildDescription(Locale locale) {
		return descriptionSupplier.getDescription(getActualValue(), locale);
	}
}
