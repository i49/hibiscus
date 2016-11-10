package com.github.i49.hibiscus.schema.types;

import java.math.BigDecimal;
import java.util.List;

import javax.json.JsonNumber;
import javax.json.JsonValue;

import com.github.i49.hibiscus.schema.Range;
import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.schema.problems.NotMoreThanMinimumProblem;
import com.github.i49.hibiscus.schema.problems.NotLessThanMaximumProblem;
import com.github.i49.hibiscus.schema.problems.LessThanMinimumProblem;
import com.github.i49.hibiscus.schema.problems.Problem;
import com.github.i49.hibiscus.schema.problems.MoreThanMaximumProblem;

/**
 * JSON number including integer.
 */
public class NumberType extends SimpleType {

	private static final NumberType DEFAULT = new DefaultNumberType();
	
	private BigDecimal minimum;
	private boolean exclusiveMinimum;
	private BigDecimal maximum;
	private boolean exclusiveMaximum;
	
	/**
	 * Returns this type with default settings.
	 * @return immutable type with default settings.
	 */
	public static NumberType getDefault() {
		return DEFAULT;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.NUMBER;
	}
	
	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		super.validateInstance(value, problems);
		JsonNumber number = (JsonNumber)value;
		BigDecimal decimal = number.bigDecimalValue();
		validateAgainstRange(decimal, problems);
	}

	/**
	 * Specifies minimum value of this type.
	 * @param value minimum value of this type.
	 * @return this type.
	 */
	public NumberType min(long value) {
		return min(BigDecimal.valueOf(value));
	}

	/**
	 * Specifies minimum value of this type.
	 * @param value minimum value of this type.
	 * @return this type.
	 */
	public NumberType min(BigDecimal value) {
		NumberType self = modifiable();
		self.minimum = value;
		return self;
	}

	/**
	 * Specifies whether the minimum value is exclusive or not.
	 * @param exclusive true if exclusive.
	 * @return this type.
	 */
	public NumberType exclusiveMin(boolean exclusive) {
		NumberType self = modifiable();
		self.exclusiveMinimum = exclusive;
		return self;
	}

	/**
	 * Specifies maximum value of this type.
	 * @param value maximum value of this type.
	 * @return this type.
	 */
	public NumberType max(long value) {
		return max(BigDecimal.valueOf(value));
	}

	/**
	 * Specifies maximum value of this type.
	 * @param value maximum value of this type.
	 * @return this type.
	 */
	public NumberType max(BigDecimal value) {
		NumberType self = modifiable();
		self.maximum = value;
		return self;
	}
	
	/**
	 * Specifies whether the maximum value is exclusive or not.
	 * @param exclusive true if exclusive.
	 * @return this type.
	 */
	public NumberType exclusiveMax(boolean exclusive) {
		NumberType self = modifiable();
		self.exclusiveMaximum = exclusive;
		return self;
	}
	
	protected NumberType modifiable() {
		return this;
	}
	
	private void validateAgainstRange(BigDecimal value, List<Problem> problems) {
		if (this.minimum != null) {
			int result = value.compareTo(this.minimum);
			if (this.exclusiveMinimum) {
				if (result <= 0) {
					problems.add(new NotMoreThanMinimumProblem(value, createRange()));
				}
			} else {
				if (result < 0) {
					problems.add(new LessThanMinimumProblem(value, createRange()));
				}
			}
		}
		if (this.maximum != null) {
			int result = value.compareTo(this.maximum);
			if (this.exclusiveMaximum) {
				if (result >= 0) {
					problems.add(new NotLessThanMaximumProblem(value, createRange()));
				}
			} else {
				if (result > 0) {
					problems.add(new MoreThanMaximumProblem(value, createRange()));
				}
			}
		}
	}

	/**
	 * Creates range of number allowed for this type.
	 * @return range of number.
	 */
	private Range<BigDecimal> createRange() {
		return Range.of(this.minimum, this.maximum, this.exclusiveMinimum, this.exclusiveMaximum);
	}
	
	/**
	 * Number type without any constraints.
	 */
	private static class DefaultNumberType extends NumberType {
		@Override
		protected NumberType modifiable() {
			return new NumberType();
		}
	}
}
