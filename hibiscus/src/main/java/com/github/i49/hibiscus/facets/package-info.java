/**
 * Provides various kinds of <i>facets</i> that allow you to restrict the value spaces of built-in types in JSON schema.
 * 
 * <h2 id="introducing-facets">1. Introducing Facets</h2>
 * 
 * <p>A <strong>facet</strong> is a single defining aspect of a value space of types.
 * Each of built-in types in JSON schema just provides default value space of its own.
 * By using facet, you can restrict the value spaces of these types as necessary.</p>
 * <p>For example, you can express restrictions listed below.</p>
 * <ul>
 * <li>valid range of numeric values, such as lower or upper threshold of the range.</li>
 * <li>the length of values, such as length of string.</li>
 * <li>list of values to be considered as valid.</li>
 * <li>predefined format of values.</li>
 * <li>regular expression that valid values must match.</li>
 * </ul>
 * <p>This package provides various kinds of facet implementations, 
 * and all these classes implement common {@link com.github.i49.hibiscus.facets.Facet Facet} interface.
 * Each facet in this package can be applied to a value in JSON document 
 * and when it detects the value is out of valid value space, 
 * it will reports one or more corresponding problems.
 * All problems to be reported by the facets are defined in {@link com.github.i49.hibiscus.problems} package.
 * </p>
 *
 * <h2 id="list-of-facets">2. List of Facets</h2>
 * 
 * <p>The table shown below lists all facets defined in this package.</p>
 * 
 * <table border="1" cellpadding="4" style="border-collapse: collapse;">
 * <caption>The list of facets</caption>
 * <tr>
 * <th>No.</th>
 * <th>Facet Name</th>
 * <th>Description</th>
 * <th>Applicable Types</th>
 * <th>Implemented By</th>
 * <th>Problem Reported</th>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>length</td>
 * <td>allows only values of a specific length.</td>
 * <td>{@code array()}, {@code string()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.LengthFacet LengthFacet}</td>
 * <td>
 * {@link com.github.i49.hibiscus.problems.ArrayLengthProblem ArrayLengthProblem},
 * {@link com.github.i49.hibiscus.problems.StringLengthProblem StringLengthProblem}
 * </td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>maxLength</td>
 * <td>restricts the upper bound of value length.</td>
 * <td>{@code array()}, {@code string()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.MaxLengthFacet MaxLengthFacet}</td>
 * <td>
 * {@link com.github.i49.hibiscus.problems.ArrayTooLongProblem ArrayTooLongProblem},
 * {@link com.github.i49.hibiscus.problems.StringTooLongProblem StringTooLongProblem}
 * </td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>minLength</td>
 * <td>restricts the lower bound of value length.</td>
 * <td>{@code array()}, {@code string()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.MinLengthFacet MinLengthFacet}</td>
 * <td>
 * {@link com.github.i49.hibiscus.problems.ArrayTooShortProblem ArrayTooShortProblem},
 * {@link com.github.i49.hibiscus.problems.StringTooShortProblem StringTooShortProblem}
 * </td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>maxInclusive</td>
 * <td>restricts the upper bound of numeric range.</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.MaxNumberFacet MaxNumberFacet}</td>
 * <td>{@link com.github.i49.hibiscus.problems.InclusiveUpperBoundProblem InclusiveUpperBoundProblem}</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>maxExclusive</td>
 * <td>same as maxInclusive while the boundary is excluded from the valid range.</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.MaxNumberFacet MaxNumberFacet}</td>
 * <td>{@link com.github.i49.hibiscus.problems.ExclusiveUpperBoundProblem ExclusiveUpperBoundProblem}</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>minInclusive</td>
 * <td>restricts the lower bound of numeric range.</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.MinNumberFacet MinNumberFacet}</td>
 * <td>{@link com.github.i49.hibiscus.problems.InclusiveLowerBoundProblem InclusiveLowerBoundProblem}</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td>minExclusive</td>
 * <td>same as minInclusive while the boundary is excluded from the valid range.</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.MinNumberFacet MinNumberFacet}</td>
 * <td>{@link com.github.i49.hibiscus.problems.ExclusiveLowerBoundProblem ExclusiveLowerBoundProblem}</td>
 * </tr>
 * <tr>
 * <td>8</td>
 * <td>pattern</td>
 * <td>restricts string values by a regular expression.</td>
 * <td>{@code string()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.PatternFacet PatternFacet}</td>
 * <td>{@link com.github.i49.hibiscus.problems.StringPatternProblem StringPatternProblem}</td>
 * </tr>
 * <tr>
 * <td>9</td>
 * <td>unique</td>
 * <td>enforces each element in array to be unique.</td>
 * <td>{@code array()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.UniqueItemFacet UniqueItemFacet}</td>
 * <td>{@link com.github.i49.hibiscus.problems.ArrayDuplicateItemProblem ArrayDuplicateItemProblem}</td>
 * </tr>
 * <tr>
 * <td>10</td>
 * <td>enumeration</td>
 * <td>restricts the value space to a set of distinct values.</td>
 * <td>{@code boolean()}, {@code integer()}, {@code number()}, {@code string()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.EnumerationFacet EnumerationFacet}</td>
 * <td>{@link com.github.i49.hibiscus.problems.NoSuchEnumeratorProblem NoSuchEnumeratorProblem}</td>
 * </tr>
 * <tr>
 * <td>11</td>
 * <td>assertion</td>
 * <td>adds arbitrary assertions on the type.</td>
 * <td>all but {@code nil()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.AssertionFacet AssertionFacet}</td>
 * <td>{@link com.github.i49.hibiscus.problems.AssertionFailureProblem AssertionFailureProblem}</td>
 * </tr>
 * <tr>
 * <td>12</td>
 * <td>format</td>
 * <td>specifies predefined format of the type.</td>
 * <td>{@code string()}</td>
 * <td>{@link com.github.i49.hibiscus.facets.FormatFacet FormatFacet}</td>
 * <td>{@link com.github.i49.hibiscus.problems.InvalidFormatProblem InvalidFormatProblem}</td>
 * </tr>
 * </table>
 */
package com.github.i49.hibiscus.facets;
