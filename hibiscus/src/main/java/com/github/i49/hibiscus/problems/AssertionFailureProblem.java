package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonValue;

/**
 * Problem that an assertion made on specific type was failed. 
 * 
 * <p>This problem can be caused by all but {@code nil()} types.</p>
 * 
 * @param <V> the type of {@link JsonValue} which caused this problem.
 */
public class AssertionFailureProblem<V extends JsonValue> extends JsonValueProblem<V> {

	private final ProblemDescriber<V> describer;
	
	/**
	 * Constructs this problem.
	 * @param value actual value which is the cause of this problem.
	 * @param describer the object which will provide the description of this problem. 
	 */
	public AssertionFailureProblem(V value, ProblemDescriber<V> describer) {
		super(value);
		this.describer = describer;
	}

	@Override
	protected String buildDescription(Locale locale) {
		return describer.describe(getActualValue(), locale);
	}
}
