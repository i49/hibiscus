/**
 * Provides a JSON validator and its supporting types.
 * 
 * <p>
 * The core interface to be used to validate JSON documents against specified schema is
 * {@link com.github.i49.hibiscus.validation.JsonValidator JsonValidator}.
 * This interface is implemented by
 * {@link com.github.i49.hibiscus.validation.BasicJsonValidator BasicJsonValidator} class
 * which is provided by this package also.
 * This class is intended to be extended by application developers 
 * to build their own validators which have application specific schema. 
 * </p>
 * <p>
 * All validation methods provided by
 * {@link com.github.i49.hibiscus.validation.JsonValidator JsonValidator}
 * will return an object implementing
 * {@link com.github.i49.hibiscus.validation.ValidationResult ValidationResult} interface
 * as result of the validation.
 * </p>
 * 
 *  @see com.github.i49.hibiscus.validation.JsonValidator
 *  @see com.github.i49.hibiscus.validation.BasicJsonValidator
 *  @see com.github.i49.hibiscus.validation.ValidationResult
 */
package com.github.i49.hibiscus.validation;
