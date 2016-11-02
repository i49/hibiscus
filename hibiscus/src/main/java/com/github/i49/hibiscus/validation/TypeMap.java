package com.github.i49.hibiscus.validation;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import com.github.i49.schema.TypeId;
import com.github.i49.schema.types.ValueType;

public class TypeMap {

	public static final TypeMap EMPTY = new TypeMap();
	
	private final Map<TypeId, ValueType> map;
	
	public static TypeMap empty() {
		return EMPTY;
	}
	
	public static TypeMap of(ValueType type) {
		return new TypeMap().addType(type);
	}
	
	public static TypeMap of(ValueType... types) {
		TypeMap map = new TypeMap();
		for (ValueType type: types) {
			map.addType(type);
		}
		return map;
	}
	
	public static TypeMap of(ValueType type, ValueType... moreTypes) {
		TypeMap map = new TypeMap();
		map.addType(type);
		for (ValueType other: moreTypes) {
			map.addType(other);
		}
		return map;
	}
	
	private TypeMap() {
		this.map = new EnumMap<>(TypeId.class);
	}

	public boolean containsType(TypeId typeId) {
		return map.containsKey(typeId);
	}

	public ValueType getType(TypeId typeId) {
		ValueType type = map.get(typeId);
		if (type != null) {
			return type;
		}
		if (typeId == TypeId.INTEGER) {
			type = map.get(TypeId.NUMBER);
			if (type != null) {
				return type;
			}
		}
		return null;
	}
	
	public Set<TypeId> getTypeIds() {
		return map.keySet();
	}

	private TypeMap addType(ValueType type) {
		if (type == null) {
			throw new IllegalArgumentException();
		}
		TypeId typeId = type.getTypeId();
		if (map.containsKey(typeId)) {
			throw new DuplicateTypeException(typeId);
		} else {
			map.put(typeId, type);
		}
		return this;
	}
}
