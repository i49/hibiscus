package com.github.i49.hibiscus.examples;

import static com.github.i49.hibiscus.schema.JsonTypes.*;

import com.github.i49.hibiscus.schema.ArrayType;
import com.github.i49.hibiscus.schema.ObjectType;
import com.github.i49.hibiscus.validation.BasicJsonValidator;
import com.github.i49.hibiscus.validation.JsonValidator;

public class ProductsValidator extends BasicJsonValidator {

	// Type to store geographical coordinates.
	private static final ObjectType geoType = object(
			optional("latitude", number()),
			optional("longitude", number())
		);
	
	private static final ArrayType schema = array(
			object(
				required("id", number()),
				required("name", string()),
				required("price", number().minExclusive(0)),
				optional("tags", array(string()).minItems(1)),
				optional("dimensions", object(
					required("length", number()),
					required("width", number()),
					required("height", number())
				)),
				optional("warehouseLocation", geoType)
			)
		);			
	
	public ProductsValidator() {
		super(schema);
	}

	public static void main(String[] args) {
		JsonValidator validator = new ProductsValidator();
		JsonLoader.load("products.json", validator);
	}
}
