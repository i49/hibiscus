package com.github.i49.hibiscus.validation;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;

import com.github.i49.hibiscus.schema.types.JsonType;

/**
 * JSON validator which will be base class of your custom validator.  
 */
public class JsonValidator {

	private final JsonType rootType;
	
	private final JsonParserFactory parserFactory;
	private final JsonBuilderFactory builderFactory;
	
	/**
	 * Constructs this validator.
	 * @param rootType expected JSON type at root, which must be array or object.
	 */
	public JsonValidator(JsonType rootType) {
		this.rootType = rootType;
		this.parserFactory = createParserFactory();
		this.builderFactory = createBuilderFactory();
	}
	
	/**
	 * Returns expected JSON type at root.
	 * @return expected JSON type at root.
	 */
	public JsonType getRootType() {
		return rootType;
	}

	/**
	 * Validates during reading JSON content which comes from text reader.
	 * @param reader a reader from which JSON is to be read.
	 * @return validation result containing value read and found problems.
	 */
	public ValidationResult validate(Reader reader) {
		try (JsonParser parser = this.parserFactory.createParser(reader)) {
			return parse(parser);
		}
	}
	
	/**
	 * Validates during reading JSON content which compes from byte stream.
	 * @param stream byte stream from which JSON is to be read.
	 * @param charset character set to be used to decode bytes into text.
	 * @return validation result containing value read and found problems.
	 */
	public ValidationResult validate(InputStream stream, Charset charset) {
		try (JsonParser parser = this.parserFactory.createParser(stream, charset)) {
			return parse(parser);
		}
	}
	
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
