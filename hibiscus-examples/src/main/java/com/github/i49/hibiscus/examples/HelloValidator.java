package com.github.i49.hibiscus.examples;

import static com.github.i49.hibiscus.schema.JsonTypes.*;

import com.github.i49.hibiscus.schema.ObjectType;
import com.github.i49.hibiscus.validation.BasicJsonValidator;
import com.github.i49.hibiscus.validation.JsonValidator;

public class HelloValidator extends BasicJsonValidator {

	private static final ObjectType schema = object(
			required("greeting", string())
		);

	public HelloValidator() {
		super(schema);
	}

	public static void main(String[] args) {
		JsonValidator validator = new HelloValidator();
		JsonLoader.load("hello.json", validator);
	}	
}
