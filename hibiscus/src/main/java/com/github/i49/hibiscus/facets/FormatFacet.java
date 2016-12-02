package com.github.i49.hibiscus.facets;

import java.util.List;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.formats.Format;
import com.github.i49.hibiscus.problems.InvalidFormatProblem;
import com.github.i49.hibiscus.problems.Problem;

/**
 * Facet that specifies the formats of the value. 
 * @param <V> the type of JSON values.
 */
public class FormatFacet<V extends JsonValue> implements Facet<V> {

	private final Set<Format<V>> formats;

	/**
	 * Constructs this facet.
	 * @param formats the formats allowed for the values. 
	 */
	public FormatFacet(Set<Format<V>> formats) {
		this.formats = formats;
	}

	@Override
	public void apply(V value, List<Problem> problems) {
		for (Format<V> format: formats) {
			if (!format.matches(value)) {
				problems.add(new InvalidFormatProblem<V>(value, formats));
			}
		}
	}
}
