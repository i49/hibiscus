package com.github.i49.hibiscus.facets;

import java.util.List;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.formats.Format;
import com.github.i49.hibiscus.problems.InvalidFormatProblem;
import com.github.i49.hibiscus.problems.Problem;

/**
 * <strong>format</strong> facet to select predefined format for the type. 
 * <p>
 * This facet is applicable to {@code string()} type only.
 * If the value of the type does not match any of the specified formats,
 * {@link InvalidFormatProblem} will be reported by this facet. 
 * All formats currently supported are provided by 
 * {@link com.github.i49.hibiscus.formats} package and can be obtained
 * by methods of {@link com.github.i49.hibiscus.formats.Formats Formats} class.
 * </p>
 *
 * @param <V> the type of {@link JsonValue} to which this facet will be applied.
 * 
 * @see com.github.i49.hibiscus.formats
 * @see com.github.i49.hibiscus.formats.Format
 * @see com.github.i49.hibiscus.formats.Formats
 */
public class FormatFacet<V extends JsonValue> implements Facet<V> {

	private final Set<Format<V>> formats;

	/**
	 * Constructs this facet.
	 * @param formats the set of formats allowed for the type. 
	 */
	public FormatFacet(Set<Format<V>> formats) {
		this.formats = formats;
	}

	@Override
	public void apply(V value, List<Problem> problems) {
		for (Format<V> format: formats) {
			if (!format.matches(value)) {
				problems.add(new InvalidFormatProblem<V>(formats));
			}
		}
	}
}
