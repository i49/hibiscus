/**
 * Provides classes representing problems that will be detected by validation of JSON document.
 * 
 * <p>
 * When the JSON validator verifies a JSON document, 
 * it reports detected violations against given schema as <i>problems</i>.
 * Each kind of problems is encapsulated in its own class defined in this package. 
 * All these problem classes implement the common interface 
 * {@link com.github.i49.hibiscus.problems.Problem Problem}.
 * </p>
 * 
 * <p>Please read {@link com.github.i49.hibiscus.problems.Problem Problem} for more detail about problems.</p>
 * 
 * @see com.github.i49.hibiscus.problems.Problem
 */
package com.github.i49.hibiscus.problems;
