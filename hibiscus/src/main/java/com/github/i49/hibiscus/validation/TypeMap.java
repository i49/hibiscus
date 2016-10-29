package com.github.i49.hibiscus.validation;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TypeMap {

	public static final TypeMap TYPEMAP_OF_ANY = TypeMap.of(AnyType.INSTANCE);
	
	private final Map<TypeId, ValueType> map;
	private final boolean anyType;
	
	public static TypeMap ofAny() {
		return TYPEMAP_OF_ANY;
	}
	
	public static TypeMap of(ValueType type) {
		Map<TypeId, ValueType> map = new EnumMap<>(TypeId.class);
		boolean anyType = (type == AnyType.INSTANCE);
		if (!anyType) {
			map.put(type.getTypeId(), type);
		}
		return new TypeMap(map, anyType);
	}
	
	public static TypeMap of(ValueType... types) {
		Map<TypeId, ValueType> map = new EnumMap<>(TypeId.class);
		boolean anyType = false;
		for (ValueType type: types) {
			if (type == AnyType.INSTANCE) {
				anyType = true;
			} else {
				TypeId typeId = type.getTypeId();
				if (map.containsKey(typeId)) {
					throw new DuplicateTypeException(typeId);
				} else {
					map.put(typeId, type);
				}
			}
		}
		return new TypeMap(map, anyType);
	}
	
	private TypeMap(Map<TypeId, ValueType> map, boolean anyType) {
		this.map = map;
		this.anyType = anyType;
	}
	
	public boolean containsAnyType() {
		return anyType;
	}
	
	public boolean containsType(TypeId typeId) {
		if (containsAnyType()) {
			return true;
		}
		return map.containsKey(typeId);
	}

	public ValueType getType(TypeId typeId) {
		ValueType type = map.get(typeId);
		if (type == null && containsAnyType()) {
			type = AnyType.INSTANCE;
		}
		return type;
	}
	
	public Set<TypeId> getTypeIds() {
		return map.keySet();
	}
	
	@Override
	public String toString() {
		return map.values().stream().map(ValueType::toString).collect(Collectors.joining(", "));
	}
}
