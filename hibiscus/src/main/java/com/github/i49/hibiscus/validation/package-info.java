/**
 * Provides a JSON validator and its supporting types.
 * 
 * <h2>1. JsonValidator and BasicJsonValidator</h2>
 * 
 * <p>The core interface to be used to validate JSON documents against specified schema is
 * {@link com.github.i49.hibiscus.validation.JsonValidator JsonValidator}.
 * This interface is implemented by
 * {@link com.github.i49.hibiscus.validation.BasicJsonValidator BasicJsonValidator} class
 * which is provided by this package also.
 * This class is intended to be extended by application developers 
 * to build their own validators which have application specific schema. 
 * </p>
 * <p>{@link com.github.i49.hibiscus.validation.JsonValidator JsonValidator} interface provides 
 * three variations of the methods to validate input JSON documents.</p>
 * <ol>
 * <li>A method to read JSON documents from {@link java.io.Reader}.</li>
 * <li>A method to read JSON documents from {@link java.io.InputStream} with character encoding specified explicitly.</li>
 * <li>A method to read JSON documents from {@link java.io.InputStream} with character encoding determined automatically.</li>
 * </ol>
 * <p>All these validation methods will return an object implementing
 * {@link com.github.i49.hibiscus.validation.ValidationResult ValidationResult} interface
 * as result of the validation.
 * </p> 
 * 
 * <h2>2. ValidationResult</h2>
 * 
 * <p>This interface is to be used for inspecting the result of the validation
 * and provides the following information for validator users.
 * </p>
 * <ul>
 * <li><p>JSON values parsed.</p>
 * <p>{@link com.github.i49.hibiscus.validation.ValidationResult#getValue() ValidationResult.getValue()}
 * returns the JSON value found at the root of the input JSON document including its descendant values.
 * The classes representing the values returned by this method are all defined in
 * <a href="http://json-processing-spec.java.net/">JSR 353: Java API for JSON Processing.</a>
 * </p>
 * </li>
 * <li><p>Problems detected.</p>
 * <p>{@link com.github.i49.hibiscus.validation.ValidationResult#getProblems() ValidationResult.getProblems()}
 * returns all problems against the schema found in the process of the validation.
 * These problems returned are encapsulated as classes provided by
 * {@link com.github.i49.hibiscus.problems} package.</p>
 * </li>
 * </ul>
 * 
 * @see com.github.i49.hibiscus.problems
 */
package com.github.i49.hibiscus.validation;
