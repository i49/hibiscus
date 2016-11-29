package com.github.i49.hibiscus.schema;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;

public class TypeSetTest {

	public static class EmtpyTest {
		
		@Test
		public void empty() {
			TypeSet typeSet = TypeSet.empty();
			assertEquals(0, typeSet.getTypeIds().size());
		}
	}
	
	public static class ConstructionTest {
		
		@Test
		public void singleType() {
			StringType s = string();
			TypeSet typeSet = TypeSet.of(s);
			assertEquals(1, typeSet.getTypeIds().size());
			assertEquals(s, typeSet.getType(TypeId.STRING));
		}
		
		@Test
		public void multipleTypes() {
			StringType s = string();
			IntegerType i = integer();
			BooleanType b = bool();
			TypeSet typeSet = TypeSet.of(s, i, b);
			assertEquals(3, typeSet.getTypeIds().size());
			assertEquals(s, typeSet.getType(TypeId.STRING));
			assertEquals(i, typeSet.getType(TypeId.INTEGER));
			assertEquals(b, typeSet.getType(TypeId.BOOLEAN));
		}

		@Test
		public void oneAndOtherTypes() {
			StringType s = string();
			IntegerType i = integer();
			BooleanType b = bool();
			JsonType[] types = new JsonType[]{i, b};
			TypeSet typeSet = TypeSet.of(s, types);
			assertEquals(3, typeSet.getTypeIds().size());
			assertEquals(s, typeSet.getType(TypeId.STRING));
			assertEquals(i, typeSet.getType(TypeId.INTEGER));
			assertEquals(b, typeSet.getType(TypeId.BOOLEAN));
		}
	}
	
	public static class NullTypeTest {
		
		@Test(expected = SchemaException.class)
		public void singleTypeIsNull() {
			try {
				TypeSet.of(getNull());
			} catch (SchemaException e) {
				throw e;
			}
		}
		
		@Test(expected = SchemaException.class)
		public void oneTypeIsNull() {
			try {
				TypeSet.of(string(), getNull(), bool());
			} catch (SchemaException e) {
				throw e;
			}
		}
		
		@Test(expected = SchemaException.class)
		public void firstTypeIsNull() {
			try {
				TypeSet.of(getNull(), new JsonType[]{integer(), bool()});
			} catch (SchemaException e) {
				throw e;
			}
		}

		@Test(expected = SchemaException.class)
		public void oneOfOtherTypesIsNull() {
			try {
				TypeSet.of(string(), new JsonType[]{getNull(), bool()});
			} catch (SchemaException e) {
				throw e;
			}
		}
	}
	
	public static class DuplicateTypeTest {
	
		@Test(expected = SchemaException.class)
		public void oneOfTypesIsDulicated() {
			try {
				TypeSet.of(string(), integer(), string());
			} catch (SchemaException e) {
				throw e;
			}
		}
		
		@Test(expected = SchemaException.class)
		public void oneOfOtherTypesIsDulicated() {
			try {
				TypeSet.of(string(), new JsonType[]{integer(), string()});
			} catch (SchemaException e) {
				throw e;
			}
		}
	}
	
	public static class GetTypeTest {
		
		@Test
		public void existent() {
			ArrayType arrayType = array();
			BooleanType boolType = bool();
			IntegerType intType = integer();
			NullType nullType = nil();
			NumberType numberType = number();
			ObjectType objectType = object();
			StringType stringType = string();
			TypeSet typeSet = TypeSet.of(arrayType, boolType, intType, nullType, numberType, objectType, stringType);
			assertEquals(arrayType, typeSet.getType(TypeId.ARRAY));
			assertEquals(boolType, typeSet.getType(TypeId.BOOLEAN));
			assertEquals(intType, typeSet.getType(TypeId.INTEGER));
			assertEquals(nullType, typeSet.getType(TypeId.NULL));
			assertEquals(numberType, typeSet.getType(TypeId.NUMBER));
			assertEquals(objectType, typeSet.getType(TypeId.OBJECT));
			assertEquals(stringType, typeSet.getType(TypeId.STRING));
		}
		
		@Test
		public void nonExistent() {
			TypeSet typeSet = TypeSet.of(string(), integer());
			assertNull(typeSet.getType(TypeId.BOOLEAN));
		}
	}
	
	private static JsonType getNull() {
		return null;
	}
}
