package com.github.i49.hibiscus.examples;

import com.github.i49.hibiscus.schema.Schema;
import com.github.i49.hibiscus.validation.BasicJsonValidator;
import com.github.i49.hibiscus.validation.JsonValidator;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;

import com.github.i49.hibiscus.schema.ObjectType;

public class DiskFreeValidator extends BasicJsonValidator {

	private static final ObjectType entry = object(
		required("total", integer()),
		required("used", integer())
	);
	
	private static final Schema schema = schema(
		object(
			required("/", entry),
			pattern("^(/[^/]+)+$", entry)
		)
	);
	
	public DiskFreeValidator() {
		super(schema);
	}

	public static void main(String[] args) {
		JsonValidator validator = new DiskFreeValidator();
		JsonLoader.load("diskfree.json", validator);
	}
}
