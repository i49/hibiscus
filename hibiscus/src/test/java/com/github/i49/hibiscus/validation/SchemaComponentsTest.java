package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.types.SchemaComponents.*;

import org.junit.Test;

public class SchemaComponentsTest {

	@Test(expected = DuplicateTypeException.class)
	public void testDuplicateType() {
		object(required("name", array(), array()));
	}
}
