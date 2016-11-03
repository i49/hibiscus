package com.github.i49.hibiscus.schema.types;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.validation.DuplicateTypeException;

/**
 * Immutable set of JSON types.
 */
public class TypeSet {

	public static final TypeSet EMPTY = new TypeSet();
	
	private final Map<TypeId, ValueType> map;
	
	public static TypeSet empty() {
		return EMPTY;
	}
	
	public static TypeSet of(ValueType type) {
		return new TypeSet().addType(type);
	}
	
	public static TypeSet of(ValueType... types) {
		TypeSet map = new TypeSet();
		for (ValueType type: types) {
			map.addType(type);
		}
		return map;
	}
	
	public static TypeSet of(ValueType type, ValueType... moreTypes) {
		TypeSet map = new TypeSet();
		map.addType(type);
		for (ValueType other: moreTypes) {
			map.addType(other);
		}
		return map;
	}
	
	private TypeSet() {
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
	
	/**
	 * Returns type identifiers in this set.
	 * @return set of type identifiers.
	 */
	public Set<TypeId> getTypeIds() {
		return map.keySet();
	}

	private TypeSet addType(ValueType type) {
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
