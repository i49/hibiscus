package com.github.i49.hibiscus.json;

import javax.json.JsonNumber;
import javax.json.JsonValue;

abstract class JsonNumberImpl implements JsonNumber {

	@Override
	public ValueType getValueType() {
		return JsonValue.ValueType.NUMBER;
	}
}
