package com.github.i49.hibiscus.schema;

import java.util.function.IntFunction;
import java.util.function.Supplier;

class Arguments {
	
	static void checkNotNull(Object object, Supplier<String> supplier) {
		if (object == null) {
			throw new SchemaException(supplier.get());
		}
	}
	
	static void checkNotNull(Object first, Object[] others, IntFunction<String> func) {
		int index = 0;
		if (first == null) {
			throw new SchemaException(func.apply(index));
		}
		for (Object object: others) {
			index++;
			if (object == null) {
				throw new SchemaException(func.apply(index));
			}
		}
	}
}
