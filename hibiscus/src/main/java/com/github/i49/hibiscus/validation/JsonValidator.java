package com.github.i49.hibiscus.validation;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.json.JsonException;
import javax.json.stream.JsonParsingException;

import com.github.i49.hibiscus.schema.JsonType;

/**
 * JSON validator.
 */
public interface JsonValidator {

	/**
	 * Returns expected JSON type to be found at root of JSON document.
	 * @return expected JSON type at root of JSON document.
	 */
	JsonType getRootType();

	/**
	 * Validates JSON document which is to be read from {@link java.io.Reader}.
	 * 
	 * @param reader the reader from which JSON document is to be read.
	 * @return validation result containing JSON values and problems found.
	 * 
	 * @exception IllegalArgumentException if reader is {@code null}.
	 * @exception JsonException if I/O error occurred while reading the document.
	 * @exception JsonParsingException if JSON document is not well-formed, that is to say broken JSON.
	 */
	ValidationResult validate(Reader reader);
	
	/**
	 * Validates JSON document which is to be read from {@link java.io.InputStream}.
	 * 
	 * @param stream the byte stream from which JSON document is to be read.
	 * @param charset the character set to be used to decode bytes into characters.
	 * @return validation result containing JSON values and problems found.
	 * 
	 * @exception IllegalArgumentException if one of arguments is {@code null}.
	 * @exception JsonException if I/O error occurred while reading the document.
	 * @exception JsonParsingException if JSON document is not well-formed, that is to say broken JSON.
	 */
	ValidationResult validate(InputStream stream, Charset charset);
}
