package com.github.i49.hibiscus.validation;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class TypeMap {

	public static final TypeMap EMPTY = new TypeMap();
	
	private final Map<TypeId, ValueType> map;
	
	public static TypeMap empty() {
		return EMPTY;
	}
	
	public static TypeMap of(ValueType type) {
		Map<TypeId, ValueType> map = new EnumMap<>(TypeId.class);
		map.put(type.getTypeId(), type);
		return new TypeMap(map);
	}
	
	public static TypeMap of(ValueType... types) {
		Map<TypeId, ValueType> map = new EnumMap<>(TypeId.class);
		for (ValueType type: types) {
			TypeId typeId = type.getTypeId();
			if (map.containsKey(typeId)) {
				throw new DuplicateTypeException(typeId);
			} else {
				map.put(typeId, type);
			}
		}
		return new TypeMap(map);
	}
	
	private TypeMap() {
		this.map = Collections.emptyMap();
	}

	private TypeMap(Map<TypeId, ValueType> map) {
		this.map = map;
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public boolean containsType(TypeId typeId) {
		if (isEmpty()) {
			return true;
		}
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
}
