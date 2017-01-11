package com.github.i49.hibiscus.schema;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.formats.Formats;
import com.github.i49.hibiscus.formats.StringFormat;
import com.github.i49.hibiscus.schema.internal.ArrayTypeImpl;
import com.github.i49.hibiscus.schema.internal.BooleanTypeImpl;
import com.github.i49.hibiscus.schema.internal.IntegerTypeImpl;
import com.github.i49.hibiscus.schema.internal.NamedPropertyImpl;
import com.github.i49.hibiscus.schema.internal.NullTypeImpl;
import com.github.i49.hibiscus.schema.internal.NumberTypeImpl;
import com.github.i49.hibiscus.schema.internal.ObjectTypeImpl;
import com.github.i49.hibiscus.schema.internal.PredicatePatternProperty;
import com.github.i49.hibiscus.schema.internal.RegexPatternProperty;
import com.github.i49.hibiscus.schema.internal.SchemaImpl;
import com.github.i49.hibiscus.schema.internal.StringTypeImpl;

/**
 * The central class used to create various kinds of schema components which can compose
 * your schema for validating JSON documents.
 * 
 * <p>These schema components can be categorized into three groups listed below:</p>
 * <ol>
 * <li><strong>schema</strong>, which is represented by {@link Schema}. </li>
 * <li><strong>build-in types</strong>, which are represented by {@link JsonType} and its subinterfaces.</li>
 * <li><strong>object properties</strong>, which are represented by {@link Property}.</li>
 * </ol>
 * 
 * <h3>1. Schema</h3>
 * 
 * <p>The {@link Schema} is the top level component of schema to be built
 * and it is composed of other schema components.</p>
 * <p>The following code shows how to create a new schema by calling {@code schema()} method.</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.schema.SchemaComponents.*;
 * Schema s = schema(type1, type2, ...);
 * </code></pre></blockquote>
 * <p>{@link #schema(JsonType...)} method shown above receives one or more {@link JsonType}s, 
 * any of which is allowed to be at the root of JSON documents.</p>
 * 
 * <h3>2. Built-in Types</h3>
 * 
 * <p>As the sample code shown above illustrates, a schema consists of several {@link JsonType}s 
 * that can be nested inside other types and form a tree-like structure as a whole. 
 * The validation will match the values in JSON document against types declared in schema 
 * from the root to the leaf location.</p>
 *
 * <p>This library provides seven built-in types which can be used to compose schema for JSON.</p>
 * <table border="1" cellpadding="4" style="border-collapse: collapse;">
 * <caption>The list of built-in types</caption>
 * <tr>
 * <th>No.</th>
 * <th>Type Name</th>
 * <th>Creation Method</th>
 * <th>Interface</th>
 * <th>Description</th>
 * <th>Sample Values</th>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>array</td>
 * <td>{@link #array()}</td>
 * <td>{@link ArrayType}</td>
 * <td>JSON array</td>
 * <td>{@code ["milk", "bread", "eggs"]}</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>bool</td>
 * <td>{@link #bool()}</td>
 * <td>{@link BooleanType}</td>
 * <td>JSON boolean</td>
 * <td>{@code true}</td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>integer</td>
 * <td>{@link #integer()}</td>
 * <td>{@link IntegerType}</td>
 * <td>JSON number without a fractional part</td>
 * <td>{@code 42}</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>number</td>
 * <td>{@link #number()}</td>
 * <td>{@link NumberType}</td>
 * <td>JSON number</td>
 * <td>{@code 3.14}</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>null</td>
 * <td>{@link #nil()}</td>
 * <td>{@link NullType}</td>
 * <td>JSON null</td>
 * <td>{@code null}</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>object</td>
 * <td>{@link #object()}</td>
 * <td>{@link ObjectType}</td>
 * <td>JSON object</td>
 * <td>{@code {"name": "John", "age": 33}}</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td>string</td>
 * <td>{@link #string()}</td>
 * <td>{@link StringType}</td>
 * <td>JSON string</td>
 * <td>{@code "hello"}</td>
 * </tr>
 * </table>
 * 
 * <p>The following code shows how to create each of these built-in types except array and object types.</p>
 * <blockquote><pre><code>
 * BooleanType b = bool();
 * IntegerType i = integer();
 * NumberType n = number();
 * NullType nil = nil();
 * StringType s = string();
 * </code></pre></blockquote>
 * 
 * <p>The code listed below shows how to create an array type which elements must be string type.</p>
 * <blockquote><pre><code>ArrayType a = array(string());</code></pre></blockquote>
 * 
 * <p>The code listed below shows how to create an object type which has two properties.</p>
 * <blockquote><pre><code>ObjectType a = object(p1, p2);</code></pre></blockquote>
 * 
 * <p>The next section introduces how to create properties for object types.</p>
 * 
 * <h3>3. Object Properties</h3>
 * <p>JSON object can contain zero or more name-value pairs which are called <i>properties</i>.
 * <h4>3.1. Basic Property</h4>
 * <p>Basic property is a property that has a name which is fixed and declared in schema beforehand.
 * Two types of basic properties are provided, which is called <i>required</i> or <i>optional</i> property respectively. 
 * They can be distinguished by whether they are mandatory or not for the containing JSON object.</p>
 * <p>The code listed below shows how to create these types of property
 * by calling {@code required()} and {@code optional()} respectively.</p>
 * <blockquote><pre><code>
 * Property p1 = required("name", string()); // this property is mandatory.
 * Property p2 = optional("age", integer()); // this property is optional.
 * </code></pre></blockquote>
 * 
 * <h4>3.2. Pattern Property</h4>
 * <p>The other type of property is a property which does not have a determined name.
 * The name of this type of property is allowed to have some variations 
 * and is specified as a <i>pattern</i>,
 * such as a regular expression or {@link Predicate} functional interface.</p>
 * <p>The code listed below creates a property 
 * which name is specified as a regular expression.</p>
 * <blockquote><pre><code>
 * Property p3 = pattern("1st|2nd|3rd|[4-8]th", string());
 * </code></pre></blockquote>
 * <p>The following code creates a property which name is specified as a lambda expression
 * and must have a length of exactly three characters.</p>
 * <blockquote><pre><code>
 * Property p4 = pattern(s-&gt;s.length() == 3, integer());
 * </code></pre></blockquote>
 */
public final class SchemaComponents {

	/**
	 * Creates an empty schema.
	 * @return created schema.
	 */
	public static Schema schema() {
		return new SchemaImpl();
	}
	
	/**
	 * Creates a schema which expects specified {@link JsonType}s at the root of JSON documents.
	 * @param types the {@link JsonType}s allowed to be at the root of JSON documents.
	 *              Each type must have a unique {@link TypeId}.
	 * @return created schema.
	 * @exception SchemaException if one of types given has the same {@link TypeId} as others or {@code null}.
	 */
	public static Schema schema(JsonType... types) {
		return schema().types(types);
	}
	
	/**
	 * Creates an array type with its elements unspecified.
	 * @return created array type.
	 */
	public static ArrayType array() {
		return new ArrayTypeImpl();
	}

	/**
	 * Creates an array type which expects specified {@link JsonType}s as types of its elements.
	 * <p>Calling this methods is equivalent to {@code array().items(...)}. </p>
	 * 
	 * @param types the {@link JsonType}s allowed for elements of this array.
	 *              Each type must have a unique {@link TypeId} and cannot be {@code null}.
	 * @return created array type.
	 * @exception SchemaException if one of types given has the same {@link TypeId} as others or {@code null}.
	 */
	public static ArrayType array(JsonType... types) {
		return array().items(types);
	}

	/**
	 * Creates a boolean type.
	 * @return created boolean type.
	 */
	public static BooleanType bool() {
		return new BooleanTypeImpl();
	}

	/**
	 * Creates an integer type.
	 * @return created integer type.
	 */
	public static IntegerType integer() {
		return new IntegerTypeImpl();
	}
	
	/**
	 * Creates a number type. 
	 * @return created number type.
	 */
	public static NumberType number() {
		return new NumberTypeImpl();
	}
	
	/**
	 * Creates a null type.
	 * Note that {@link NullType} is immutable and cannot be modified.
	 * @return created null type.
	 */
	public static NullType nil() {
		return NullTypeImpl.INSTANCE;
	}

	/**
	 * Creates an object type with its properties unspecified.
	 * @return an object type.
	 */
	public static ObjectType object() {
		return new ObjectTypeImpl();
	}
	
	/**
	 * Creates an object type which expects specified properties as its members.
	 * <p>Calling this methods is equivalent to {@code object().properties(...)}.</p> 
	 * 
	 * @param properties the properties which created object may contain. Each property cannot be {@code null}. 
	 * @return created object type.
	 * @exception SchemaException if one of properties specified is {@code null}.
	 */
	public static ObjectType object(Property... properties) {
		return object().properties(properties);
	}
	
	/**
	 * Creates a string type.
	 * @return created string type.
	 */
	public static StringType string() {
		return new StringTypeImpl();
	}
	
	/**
	 * Creates an object property which is optional for the containing object.
	 * 
	 * @param name the name of the property. Cannot be {@code null}.
	 * @param type the type of the property value. Cannot be {@code null}.
	 * @param moreTypes the other types allowed for the property value. Each type cannot be {@code null}.
	 * @return created object property.
	 * @exception SchemaException if name is {@code null} or
	 *                            if one of types has the same {@link TypeId} as others or {@code null}.
	 */
	public static NamedProperty optional(String name, JsonType type, JsonType... moreTypes) {
		return new NamedPropertyImpl(name, type, moreTypes, false);
	}
	
	/**
	 * Creates an object property which is required for the containing object.
	 * 
	 * @param name the name of the property. Cannot be {@code null}.
	 * @param type the type of the property value. Cannot be {@code null}.
	 * @param moreTypes the other types allowed for the property value. Each type cannot be {@code null}.
	 * @return created object property.
	 * @exception SchemaException if name is {@code null} or
	 *                            if one of types has the same {@link TypeId} as others or {@code null}.
	 */
	public static NamedProperty required(String name, JsonType type, JsonType... moreTypes) {
		return new NamedPropertyImpl(name, type, moreTypes, true);
	}
	
	/**
	 * Creates an object property which name matches the pattern specified as a Java regular expression.
	 * Important note is that the pattern specified for this method must be compatible with Java regular expression,
	 * not with JavaScript alternative defined in the ECMA 262 specification.
	 * 
	 * @param pattern the pattern of the name specified as a Java regular expression. Cannot be {@code null}.
	 * @param type the type of the property value. Cannot be {@code null}.
	 * @param moreTypes the other types allowed for the property value. Each type cannot be {@code null}.
	 * @return created object property.
	 * @exception SchemaException if pattern is {@code null} or
	 *                            if one of types has the same {@link TypeId} as others or {@code null}.
	 * @exception PatternSyntaxException if pattern's syntax is invalid.
	 * 
	 * @see Pattern
	 */
	public static Property pattern(String pattern, JsonType type, JsonType... moreTypes) {
		return new RegexPatternProperty(pattern, type, moreTypes);
	}
	
	/**
	 * Creates an object property which name matches the pattern specified
	 * as a {@link Predicate} functional interface that can be a lambda expression.
	 * 
	 * <p>Each {@link StringFormat} provided by {@link Formats} class, such as {@link Formats#datetime()}
	 * or {@link Formats#email()}, can be utilized as a predicate and be passed in to this method.
	 * </p>
	 * 
	 * @param predicate the predicate to determine whether the name of the property is acceptable or not.
	 *                  Cannot be {@code null}. 
	 * @param type the type of the property value. Cannot be {@code null}.
	 * @param moreTypes the other types allowed for the property value. Each type cannot be {@code null}.
	 * @return created object property.
	 * @exception SchemaException if predicate is {@code null} or
	 *                            if one of types has the same {@link TypeId} as others or {@code null}.
	 */
	public static Property pattern(Predicate<String> predicate, JsonType type, JsonType... moreTypes) {
		return new PredicatePatternProperty(predicate, type, moreTypes);
	}
	
	private SchemaComponents() {
	}
}
