package com.github.i49.hibiscus.validation;

import org.junit.Test;

import static com.github.i49.hibiscus.validation.SchemaComponents.*;

public class SchemaComponentsTest {

	@Test(expected = DuplicateTypeException.class)
	public void testDuplicateType() {
		object(required("name", array(), array()));
	}
}
