package com.github.i49.hibiscus;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.github.i49.hibiscus.Schema.*;

public class JsonValidatorTest {

	@Test
	public void test() throws Exception {
		JsonNode json = loadJson("person.json");
		JsonValidator v = createPersonValidator();
		v.validate(json);
	}
	
	private JsonValidator createPersonValidator() {
		return new JsonValidator() {
			@Override
			protected Type getSchema() {
				return object();
			}
		};
	}

	private static JsonNode loadJson(String name) throws IOException {
		try (InputStream in = JsonValidatorTest.class.getResourceAsStream(name)) {
			return new ObjectMapper().readTree(in); 
		}
	}
}
