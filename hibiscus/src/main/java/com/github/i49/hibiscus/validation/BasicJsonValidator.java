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

import com.github.i49.hibiscus.json.JsonValueFactory;
import com.github.i49.hibiscus.json.JsonValueFactoryImpl;
import com.github.i49.hibiscus.schema.Schema;

/**
 * Implementation class of {@link JsonValidator} and base class of all custom JSON validators.  
 */
public class BasicJsonValidator implements JsonValidator {

	private final Schema schema;
	
	private final JsonParserFactory parserFactory;
	private final JsonBuilderFactory builderFactory;
	private final JsonValueFactory valueFactory;
	
	/**
	 * Constructs this validator.
	 * 
	 * @param schema the schema to be used by this validator to validate JSON documents.
	 * 
	 * @exception IllegalArgumentException if schema is {@code null}.
	 * @exception IllegalStateException if one of internal objects was not configured properly.
	 */
	public BasicJsonValidator(Schema schema) {
		if (schema == null) {
			throw new IllegalArgumentException("schema is null.");
		}
		this.schema = schema;
		this.parserFactory = createParserFactory();
		if (this.parserFactory == null) {
			throw new IllegalStateException("Failed to create a JsonParserFactory object.");
		}
		this.builderFactory = createBuilderFactory();
		if (this.builderFactory == null) {
			throw new IllegalStateException("Failed to create a JsonBuilderFactory object.");
		}
		this.valueFactory = createValueFactory();
		if (this.valueFactory == null) {
			throw new IllegalStateException("Failed to create a JsonValueFactory object.");
		}
	}

	@Override
	public Schema getSchema() {
		return schema;
	}

	@Override
	public ValidationResult validate(Reader reader) {
		if (reader == null) {
			throw new IllegalArgumentException("reader is null.");
		}
		try (JsonParser parser = this.parserFactory.createParser(reader)) {
			return parse(parser);
		}
	}
	
	@Override
	public ValidationResult validate(InputStream stream) {
		if (stream == null) {
			throw new IllegalArgumentException("stream is null.");
		}
		try (JsonParser parser = this.parserFactory.createParser(stream)) {
			return parse(parser);
		}
	}
	
	@Override
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
	 * @param parser the parser to parse the JSON document.
	 * @return the result of the validation.
	 */
	private ValidationResult parse(JsonParser parser) {
		JsonValidatingReader reader = new JsonValidatingReader(parser, this.builderFactory, this.valueFactory);
		JsonValue value = reader.readAll(getSchema());
		return new ValidationResultImpl(value, reader.getProblems());
	}
	
	/**
	 * Creates and configures {@link javax.json.stream.JsonParserFactory} object which implements JSON Processing API.
	 * @return created {@link javax.json.stream.JsonParserFactory} object.
	 */
	protected JsonParserFactory createParserFactory() {
		Map<String, ?> config = new HashMap<>();
		return Json.createParserFactory(config);
	}
	
	/**
	 * Creates and configures {@link javax.json.JsonBuilderFactory} object which implements JSON Processing API.
	 * @return created {@link javax.json.JsonBuilderFactory} object.
	 */
	protected JsonBuilderFactory createBuilderFactory() {
		Map<String, ?> config = new HashMap<>();
		return Json.createBuilderFactory(config);
	}
	
	/**
	 * Creates and configures {@link JsonValueFactory} object.
	 * @return created {@link JsonValueFactory} object.
	 */
	protected JsonValueFactory createValueFactory() {
		return new JsonValueFactoryImpl();
	}
}
