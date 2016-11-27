package com.github.i49.hibiscus.problems;

import java.util.Locale;

/**
 * Supplier of description of problem for specified locale.
 */
@FunctionalInterface
public interface DescriptionSupplier {

	/**
	 * Supplies a description of a problem for specified locale.
	 * @param locale the locale for which a description is supplied.
	 * @return a description of a problem.
	 */
	String getDescription(Locale locale);
}
