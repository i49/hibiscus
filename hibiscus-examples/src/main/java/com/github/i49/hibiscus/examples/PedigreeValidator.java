package com.github.i49.hibiscus.examples;

import com.github.i49.hibiscus.validation.JsonValidator;
import com.github.i49.hibiscus.schema.types.ObjectType;
import static com.github.i49.hibiscus.schema.types.JsonTypes.*;

public class PedigreeValidator extends JsonValidator{

	private static final ObjectType horse = object(
			required("name", string()),
			required("birthYear", integer())
		);

	public PedigreeValidator() {
		super(horse);
	}

	public static void main(String[] args) {
		JsonValidator validator = new PedigreeValidator();
		JsonLoader.load("pedigree.json", validator);
	}
}
