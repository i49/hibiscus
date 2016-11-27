package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonValue;

/**
 * Problem that assertion on specific type was failed. 
 */
public class AssertionFailureProblem extends ValueProblem<JsonValue> {

	private final DescriptionSupplier descriptionSupplier;
	
	/**
	 * Constructs this problem.
	 * @param value the actual value that is the cause of this problem.
	 * @param descriptionSupplier supplying the description of this problem. 
	 */
	public AssertionFailureProblem(JsonValue value, DescriptionSupplier descriptionSupplier) {
		super(value);
		this.descriptionSupplier = descriptionSupplier;
	}

	@Override
	protected String buildDescription(Locale locale) {
		return descriptionSupplier.getDescription(locale);
	}
}
