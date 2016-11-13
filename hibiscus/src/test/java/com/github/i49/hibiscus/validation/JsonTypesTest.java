package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;

import org.junit.Test;

public class JsonTypesTest {

	@Test(expected = DuplicateTypeException.class)
	public void testDuplicateType() {
		object(required("name", array(), array()));
	}
}
