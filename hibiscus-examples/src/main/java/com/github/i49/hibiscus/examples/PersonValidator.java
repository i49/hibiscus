package com.github.i49.hibiscus.examples;

import com.github.i49.hibiscus.validation.JsonValidator;
import com.github.i49.hibiscus.validation.ObjectType;
import static com.github.i49.hibiscus.validation.SchemaComponents.*;

public class PersonValidator extends JsonValidator {

	private static final ObjectType schema = object(
			required("firstName", string()),
			required("lastName", string()),
			optional("age", integer()),
			optional("hobbies", array(string()))
		);			

	public PersonValidator() {
		super(schema);
	}

	public static void main(String[] args) {
		JsonValidator validator = new PersonValidator();
		JsonLoader.load("person.json", validator);
	}
}
