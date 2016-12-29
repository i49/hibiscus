package com.github.i49.hibiscus.validation;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.json.JsonException;
import javax.json.stream.JsonParsingException;

import com.github.i49.hibiscus.schema.Schema;

/**
 * A core interface to be used to validate JSON documents against specified schema.
 * An implementation of this interface is provided by {@link BasicJsonValidator} class.
 * 
 * <p>This interface provides three variations of the methods to validate input JSON documents.</p>
 * <ol>
 * <li>A method to read JSON documents from {@link Reader}.</li>
 * <li>A method to read JSON documents from {@link InputStream} with character encoding specified explicitly.</li>
 * <li>A method to read JSON documents from {@link InputStream} with character encoding determined automatically.</li>
 * </ol>
 * <p>The following sample code shows how to validate the JSON document read from {@link Reader}.</p>
 * <blockquote><pre><code>
 * JsonValidator validator = ...;
 * ValidationResult result = null; 
 * try (Reader reader = new FileReader("person.json")) {
 *   result = validator.validate(reader);
 * }
 * </code></pre></blockquote>
 * 
 * <p>The following sample code shows how to validate the JSON document read from {@link InputStream}
 * with character encoding specified.</p>
 * <blockquote><pre><code>
 * JsonValidator validator = ...;
 * ValidationResult result = null; 
 * try (InputStream stream = Files.newInputStream(Paths.get("person.json")) {
 *   result = validator.validate(stream, StandardCharsets.UTF_8);
 * }
 * </code></pre></blockquote>
 * 
 * <p>If the input JSON document is turned to be <i>not well-formed</i>, 
 * in other words, broken as a JSON document,
 * all validation methods will throw {@link JsonParsingException} immediately 
 * in the middle of the validation.
 * Otherwise the validation methods will complete the validation until reaching the end of the document
 * and return {@link ValidationResult} 
 * even when the input JSON document has some errors against the given schema.  
 * The final result of the validation can be inspected through {@link ValidationResult}
 * returned from the validation methods.
 * </p>
 * 
 * @see BasicJsonValidator
 * @see ValidationResult
 */
public interface JsonValidator {

	/**
	 * Returns the schema against which this validator verifies JSON documents.
	 * @return the schema assigned to this validator.
	 */
	Schema getSchema();

	/**
	 * Validates a JSON document which is to be read from {@link java.io.Reader}.
	 * 
	 * @param reader the reader from which the JSON document is to be read.
	 * @return the validation result containing the JSON values and the problems detected
	 * in the process of the validation.
	 * 
	 * @exception IllegalArgumentException if reader is {@code null}.
	 * @exception JsonException if I/O error occurred while reading the document.
	 * @exception JsonParsingException if JSON document is not well-formed.
	 */
	ValidationResult validate(Reader reader);
	
	/**
	 * Validates a JSON document which is to be read from {@link java.io.InputStream}.
	 * The character encoding of the stream is determined as specified in RFC 4627.
	 * 
	 * @param stream the byte stream from which the JSON document is to be read.
	 * @return the validation result containing the JSON values and the problems detected
	 * in the process of the validation.
	 * 
	 * @exception IllegalArgumentException if stream is {@code null}.
	 * @exception JsonException if encoding cannot be determined 
	 *                          or I/O error occurred while reading the document.
	 * @exception JsonParsingException if JSON document is not well-formed.
	 * 
	 * @see <a href="https://tools.ietf.org/rfc/rfc4627.txt">RFC 4627: The application/json Media Type for JavaScript Object Notation (JSON)
</a>
	 */
	ValidationResult validate(InputStream stream);
	
	/**
	 * Validates a JSON document which is to be read from {@link java.io.InputStream}
	 * with specific character encoding.
	 * 
	 * @param stream the byte stream from which the JSON document is to be read.
	 * @param charset the character set to be used to decode bytes into characters.
	 * @return the validation result containing the JSON values and the problems detected
	 * in the process of the validation.
	 * 
	 * @exception IllegalArgumentException if one of arguments is {@code null}.
	 * @exception JsonException if I/O error occurred while reading the document.
	 * @exception JsonParsingException if JSON document is not well-formed.
	 */
	ValidationResult validate(InputStream stream, Charset charset);
}
