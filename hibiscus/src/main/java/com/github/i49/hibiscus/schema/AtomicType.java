package com.github.i49.hibiscus.schema;

/**
 * A {@link JsonType} which is not composed of other {@link JsonType}s.
 * The classes derived from this type include boolean, integer, number, null and string type.
 * Array and object type are excluded from the list.  
 */
public interface AtomicType extends JsonType {
}
