package com.github.i49.hibiscus.schema;

import java.util.regex.PatternSyntaxException;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static com.github.i49.hibiscus.schema.JsonTypes.*;

@RunWith(Enclosed.class)
public class StringTypeTest {
	
	public static class MinLengthTest {

		@Test
		public void positiveLength() {
			string().minLength(1);
		}

		@Test
		public void zeroLength() {
			string().minLength(0);
		}

		@Test(expected = SchemaException.class)
		public void negativeLength() {
			string().minLength(-1);
		}
	}

	public static class MaxLengthTest {

		@Test
		public void positiveLength() {
			string().maxLength(1);
		}

		@Test
		public void zeroLength() {
			string().maxLength(0);
		}

		@Test(expected = SchemaException.class)
		public void negativeLength() {
			string().maxLength(-1);
		}
	}
	
	public static class ValuesTest {
	
		@Test(expected = SchemaException.class)
		public void valueIsNull() {
			string().values("January", null, "March");
		}
	}

	public static class PatternTest {
		
		@Test(expected = SchemaException.class)
		public void patternIsNull() {
			string().pattern(null);
		}
		
		@Test(expected = PatternSyntaxException.class)
		public void invalidSyntax() {
			string().pattern("\\");
		}
	}
}
