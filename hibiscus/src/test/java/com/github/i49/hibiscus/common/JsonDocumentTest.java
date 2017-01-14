package com.github.i49.hibiscus.common;

import static org.junit.Assert.*;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

public class JsonDocumentTest {

	public static class PointerTest {
		
		@Test
		public void rootValue() {
			JsonDocument document = new JsonDocument();
			document.setRootValue(loadJson());
			JsonPointer p = pointer();
			assertThat(document.getValueByPointer(p).getValueType(), is(JsonValue.ValueType.OBJECT));
		}
		
		@Test
		public void objectMember() {
			JsonDocument document = new JsonDocument();
			document.setRootValue(loadJson());
			JsonPointer p = pointer("address", "state");
			JsonValue v = document.getValueByPointer(p);  
			assertThat(v.getValueType(), is(JsonValue.ValueType.STRING));
			assertThat(((JsonString)v).getString(), equalTo("NY"));
		}

		@Test
		public void arrayItem() {
			JsonDocument document = new JsonDocument();
			document.setRootValue(loadJson());
			JsonPointer p = pointer("phoneNumber", 1, "number");
			JsonValue v = document.getValueByPointer(p);  
			assertThat(v.getValueType(), is(JsonValue.ValueType.STRING));
			assertThat(((JsonString)v).getString(), equalTo("646 555-4567"));
		}
		
		private static JsonPointer pointer(Object...tokens) {
			JsonPointer.Builder builder = JsonPointer.builder();
			for (Object token: tokens) {
				if (token instanceof Integer) {
					builder.append(((Integer)token).intValue());
				} else{
					builder.append((String)token);
				}
			}
			return builder.build();
		}
	}

	private static JsonValue loadJson() {
		
		String json = "{"
				+ "\"firstName\": \"John\", \"lastName\": \"Smith\", \"age\": 25,"
				+ "\"address\" : {"
				+ "\"streetAddress\": \"21 2nd Street\","
				+ "\"city\": \"New York\","
				+ "\"state\": \"NY\","
				+ "\"postalCode\": \"10021\""
				+ "},"
				+ "\"phoneNumber\": ["
				+ "{\"type\": \"home\", \"number\": \"212 555-1234\"},"
   				+ "{\"type\": \"fax\", \"number\": \"646 555-4567\"}"
   				+ "]"
				+ "}";
		
		JsonReader reader = Json.createReader(new StringReader(json));
		return reader.read();
	}
}
