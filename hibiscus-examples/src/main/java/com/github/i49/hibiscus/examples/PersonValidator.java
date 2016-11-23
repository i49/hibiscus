package com.github.i49.hibiscus.examples;

import static com.github.i49.hibiscus.schema.JsonTypes.*;

import com.github.i49.hibiscus.schema.Schema;
import com.github.i49.hibiscus.validation.BasicJsonValidator;
import com.github.i49.hibiscus.validation.JsonValidator;

public class PersonValidator extends BasicJsonValidator {

	private static final Schema schema = schema(
		object(
			required("firstName", string()),
			required("lastName", string()),
			optional("age", integer()),
			optional("hobbies", array(string()))
		)
	);			

	public PersonValidator() {
		super(schema);
	}

	public static void main(String[] args) {
		JsonValidator validator = new PersonValidator();
		JsonLoader.load("person.json", validator);
		JsonLoader.load("person-bad.json", validator);
	}
}
