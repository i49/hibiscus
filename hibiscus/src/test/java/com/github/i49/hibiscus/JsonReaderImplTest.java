package com.github.i49.hibiscus;

import static org.junit.Assert.*;

import java.io.StringReader;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonStructure;

import org.junit.Test;

public class JsonReaderImplTest {

	@Test
	public void test() {

		String json = "{\n"
				+ "    \"first_name\": \"Jason\",\n"
				+ "    \"last_name\": \"Bourne\",\n"
				+ "    \"age\": 46,\n"
				+ "    \"hobbies\": [\"shooting\", \"recollection\"]\n"
				+ "}";
		
		JsonStructure root = null;
		try (JsonReaderImpl reader = new JsonReaderImpl(new StringReader(json))) {
			root = reader.read();
		}
		
		assertTrue(root instanceof JsonObject);
		JsonObject object = (JsonObject)root;
		assertEquals("Jason", object.getString("first_name"));
		assertEquals("Bourne", object.getString("last_name"));
		assertEquals(46, object.getInt("age"));
		
		JsonArray hobbies = object.getJsonArray("hobbies");
		assertNotNull(hobbies);
		assertEquals("shooting", ((JsonString)hobbies.get(0)).getString());
		assertEquals("recollection", ((JsonString)hobbies.get(1)).getString());
	}
}
