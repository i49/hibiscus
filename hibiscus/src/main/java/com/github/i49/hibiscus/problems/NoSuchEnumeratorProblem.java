package com.github.i49.hibiscus.problems;

import java.util.Locale;
import java.util.Set;

import javax.json.JsonValue;

/**
 * Problem that the value in JSON document does not match any enumerators allowed for the type.
 */
public class NoSuchEnumeratorProblem extends ValueProblem<JsonValue> {

	private final Set<Object> enumerators;
	
	/**
	 * Constructs this problem.
	 * @param value the value in JSON instance.
	 * @param enumerators the set of the distinct values allowed for the type.
	 */
	public NoSuchEnumeratorProblem(JsonValue value, Set<Object> enumerators) {
		super(value);
		this.enumerators = enumerators;
	}
	
	/**
	 * Returns the set of the distinct values allowed for the type.
	 * @return the set of the distinct values.
	 */
	public Set<Object> getEnumerators() {
		return enumerators;
	}
	
	@Override
	public String buildDescription(Locale locale) {
		return Messages.NO_SUCH_ENUMERATOR_PROBLEM(locale, getActualValue(), getEnumerators());
	}
}
