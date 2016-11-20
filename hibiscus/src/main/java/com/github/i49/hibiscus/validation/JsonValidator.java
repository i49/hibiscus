package com.github.i49.hibiscus.validation;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonException;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import javax.json.stream.JsonParsingException;

import com.github.i49.hibiscus.schema.ComplexType;
import com.github.i49.hibiscus.schema.JsonType;

/**
 * JSON validator which will be base class of your custom validator.  
 */
public class JsonValidator {

	private final JsonType rootType;
	
	private final JsonParserFactory parserFactory;
	private final JsonBuilderFactory builderFactory;
	
	/**
	 * Constructs this validator.
	 * @param rootType the expected JSON type at root of JSON document, which must be array or object.
	 * @exception IllegalArgumentException if rootType is null.
	 * @exception IllegalStateException if one of internal objects was not created successfully.
	 */
	public JsonValidator(ComplexType rootType) {
		if (rootType == null) {
			throw new IllegalArgumentException("rootType is null.");
		}
		this.rootType = rootType;
		this.parserFactory = createParserFactory();
		if (this.parserFactory == null) {
			throw new IllegalStateException("Failed to create a JsonParserFactory object.");
		}
		this.builderFactory = createBuilderFactory();
		if (this.builderFactory == null) {
			throw new IllegalStateException("Failed to create a JsonBuilderFactory object.");
		}
	}
	
	/**
	 * Returns expected JSON type at root of JSON document.
	 * @return expected JSON type at root.
	 */
	public JsonType getRootType() {
		return rootType;
	}

	/**
	 * Validates during reading JSON document which comes from text reader.
	 * @param reader the reader from which JSON content is to be read.
	 * @return validation result containing value read and found problems.
	 * @exception IllegalArgumentException if reader is null.
	 * @exception JsonException if I/O error occurred.
	 * @exception JsonParsingException if JSON document is not well-formed.
	 */
	public ValidationResult validate(Reader reader) {
		if (reader == null) {
			throw new IllegalArgumentException("reader is null.");
		}
		try (JsonParser parser = this.parserFactory.createParser(reader)) {
			return parse(parser);
		}
	}
	
	/**
	 * Validates during reading JSON document which comes from byte stream.
	 * @param stream the byte stream from which JSON content is to be read.
	 * @param charset the character set to be used to decode bytes into text.
	 * @return validation result containing value read and found problems.
	 * @exception IllegalArgumentException if one of arguments is null.
	 * @exception JsonException if I/O error occurred.
	 * @exception JsonParsingException if JSON document is not well-formed.
	 */
	public ValidationResult validate(InputStream stream, Charset charset) {
		if (stream == null) {
			throw new IllegalArgumentException("stream is null.");
		}
		if (charset == null) {
			throw new IllegalArgumentException("charset is null.");
		}
		try (JsonParser parser = this.parserFactory.createParser(stream, charset)) {
			return parse(parser);
		}
	}
	
	/**
	 * Parses JSON document with specified parser and produces result.
	 * @param parser the parser.
	 * @return validation result.
	 */
	private ValidationResult parse(JsonParser parser) {
		JsonValidatingReader reader = new JsonValidatingReader(parser, this.builderFactory);
		JsonValue value = reader.readAll(getRootType());
		return new ValidationResult(value, reader.getProblems());
	}
	
	/**
	 * Creates JSON parser which conforms to JSON Processing API.
	 * @return JSON parser.
	 */
	protected JsonParserFactory createParserFactory() {
		Map<String, ?> config = new HashMap<>();
		return Json.createParserFactory(config);
	}
	
	/**
	 * Creates JSON builder which conforms to JSON Processing API.
	 * @return JSON builder.
	 */
	protected JsonBuilderFactory createBuilderFactory() {
		Map<String, ?> config = new HashMap<>();
		return Json.createBuilderFactory(config);
	}
}
