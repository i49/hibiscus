package com.github.i49.hibiscus.problems;

import java.util.Locale;
import java.util.Set;

import javax.json.JsonValue;

/**
 * Problem that a value does not match any value in the enumeration allowed for the type.
 * 
 * <p>This problem can be caused by
 * {@code boolean()}, {@code integer()}, {@code number()}, or {@code string()} type.</p>
 */
public class NoSuchEnumeratorProblem extends JsonValueProblem<JsonValue> {

	private final Set<Object> enumerators;
	
	/**
	 * Constructs this problem.
	 * @param value the actual value which caused this problem.
	 * @param enumerators the set of the distinct values allowed for the type.
	 */
	public NoSuchEnumeratorProblem(JsonValue value, Set<Object> enumerators) {
		super(value);
		this.enumerators = enumerators;
	}
	
	/**
	 * Returns the set of the distinct values allowed for the type.
	 * @return the set of the distinct values allowed for the type.
	 */
	public Set<Object> getEnumerators() {
		return enumerators;
	}
	
	@Override
	public String buildDescription(Locale locale) {
		return Messages.NO_SUCH_ENUMERATOR_PROBLEM(locale, getActualValue(), getEnumerators());
	}
}
