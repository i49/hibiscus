package com.github.i49.hibiscus;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.github.i49.hibiscus.SchemaObjects.*;

public class JsonValidatorTest {

	@Test
	public void test() throws Exception {
		JsonValidator v = createPersonValidator();
		JsonNode json = loadJson("person.json");
		v.validate(json);
	}
	
	private JsonValidator createPersonValidator() {
		ObjectType object = object()
				.properties(
					required("firstName", string()),
					required("lastName", string()),
					optional("age", number())
				);
		return new JsonValidator(object);
	}

	private static JsonNode loadJson(String name) throws IOException {
		try (InputStream in = JsonValidatorTest.class.getResourceAsStream(name)) {
			return new ObjectMapper().readTree(in); 
		}
	}
}
