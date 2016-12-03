package com.github.i49.hibiscus.formats;

import javax.json.JsonString;
import com.github.i49.hibiscus.schema.StringType;

/**
 * {@link Format} which can be applied to {@link StringType StringType} only.
 */
public interface StringFormat extends Format<JsonString> {
}
