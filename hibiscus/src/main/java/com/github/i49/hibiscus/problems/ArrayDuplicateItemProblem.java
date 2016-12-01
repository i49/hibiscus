package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonArray;
import javax.json.JsonValue;

/**
 * Problem that array has a duplicate element.
 */
public class ArrayDuplicateItemProblem extends ValueProblem<JsonArray> {

	private final int itemIndex;
	
	/**
	 * Constructs this problem.
	 * @param value the array value that caused this problem.
	 * @param itemIndex where the duplication was found.
	 */
	public ArrayDuplicateItemProblem(JsonArray value, int itemIndex) {
		super(value);
		this.itemIndex = itemIndex;
	}
	
	/**
	 * Returns the index of duplicate element.
	 * @return the index of duplicate element. 
	 */
	public int getDuplicateIndex() {
		return itemIndex;
	}
	
	/**
	 * Returns the duplicate item.
	 * @return the duplicate item.
	 */
	public JsonValue getDuplicateItem() {
		return getActualValue().get(itemIndex);
	}

	@Override
	protected String buildDescription(Locale locale) {
		return Messages.DUPLICATE_ITEM_PROBLEM(locale, getDuplicateIndex(), getDuplicateItem());
	}
}
