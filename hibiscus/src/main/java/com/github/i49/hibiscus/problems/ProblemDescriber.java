package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonValue;

/**
 * A helper interface to describe a problem for specified locale.
 * <p>This interface is used with {@link AssertionFailureProblem} class.</p>
 * 
 * @param <V> the type of {@link JsonValue} which caused the problem.
 */
@FunctionalInterface
public interface ProblemDescriber<V extends JsonValue> {

	/**
	 * Describes a problem for specified locale.
	 * @param value the value in JSON document which caused the problem.
	 * @param locale the locale for which a description is desired.
	 * @return a description of a problem.
	 */
	String describe(V value, Locale locale);
}
