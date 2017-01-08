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

import com.github.i49.hibiscus.schema.Schema;
import com.github.i49.hibiscus.schema.SchemaComponents;

/**
 * An implementation class of {@link JsonValidator} interface and the base class of all custom JSON validators.  
 * This class is intended to be extended by application developers 
 * to build their own validators which have application specific schema. 
 * 
 * <p>The schema to be used to validate JSON documents should be assigned to the object of this class 
 * by passing it to the constructor.
 * For more information about how to define schema, please see {@link SchemaComponents} class.
 * </p>
 * 
 * <p>The following sample code shows how to write your own JSON validator by extending this class.</p>
 * <blockquote><pre><code>
 * import com.github.i49.hibiscus.validation.BasicJsonValidator;
 * import com.github.i49.hibiscus.schema.Schema;
 * import static com.github.i49.hibiscus.schema.SchemaComponents.*;
 * 
 * public class PersonValidator extends BasicJsonValidator {
 *   // Schema definition.
 *   private static final Schema schema = schema(
 *     object(
 *       required("firstName", string()),
 *       required("lastName", string()),
 *       optional("age", integer()),
 *       optional("hobbies", array(string()))
 *     )
 *   );  
 * 
 *   public PersonValidator() {
 *     super(schema)
 *   }
 * }
 * </code></pre></blockquote>
 * 
 * <p>For details about how to validate JSON documents by using this class,
 * please see {@link JsonValidator} interface.
 * </p>
 * 
 * @see SchemaComponents
 * @see JsonValidator
 */
public class BasicJsonValidator implements JsonValidator {

	private final Schema schema;
	
	private final JsonParserFactory parserFactory;
	private final JsonBuilderFactory builderFactory;
	
	/**
	 * Constructs this validator.
	 * 
	 * @param schema the schema to be used by this validator to validate JSON documents.
	 *               Should not be modified once passed to this constructor. 
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
	 * Parses the JSON document with specified parser and produces the validation result.
	 * @param parser the parser to be used to parse the JSON document.
	 * @return the result of the validation.
	 */
	private ValidationResult parse(JsonParser parser) {
		JsonValidatingReader reader = new JsonValidatingReader(parser, this.builderFactory);
		JsonValue value = reader.readAll(getSchema());
		return new ValidationResultImpl(value, reader.getProblems());
	}
	
	/**
	 * Creates and configures {@link JsonParserFactory} object which implements Java API for JSON Processing.
	 * @return created {@link JsonParserFactory} object to be used in the process of the validation.
	 * @see JsonParserFactory
	 * @see <a href="http://json-processing-spec.java.net/">JSR 353: Java API for JSON Processing</a>
	 */
	protected JsonParserFactory createParserFactory() {
		Map<String, ?> config = new HashMap<>();
		return Json.createParserFactory(config);
	}
	
	/**
	 * Creates and configures {@link JsonBuilderFactory} object which implements Java API for JSON Processing.
	 * @return created {@link JsonBuilderFactory} object to be used in the process of the validation.
	 * @see JsonBuilderFactory
	 * @see <a href="http://json-processing-spec.java.net/">JSR 353: Java API for JSON Processing</a>
	 */
	protected JsonBuilderFactory createBuilderFactory() {
		Map<String, ?> config = new HashMap<>();
		return Json.createBuilderFactory(config);
	}
}
