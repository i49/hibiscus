# Schema Basics

## 1. Schema object

The top level component of your schema is `Schema` object that can be instantiated by calling
`SchemaComponents.schema()` class method.

```java
  import static com.github.i49.hibiscus.schema.SchemaComponents.*;
  Schema s = schema(/* types definitions here. */);  
```
The parameters of this method are types that allowed to be at root of JSON documents. All types that are useful to write schema will be introduced in the next section.

## 2. Basic types

Hibiscus offers following JSON types that can compose the schema.

name      | class         | creation method | example values
----------|---------------|-----------------|----------------------------------------
array     | `ArrayType`   | `array()`       | [1, 2, 3]
boolean   | `BooleanType` | `bool()`        | true
integer   | `IntegerType` | `integer()`     | 42
number    | `NumberType`  | `number()`      | 3.14
null      | `NullType`    | `nil()`         | null
object    | `ObjectType`  | `object()`      | { "name": "John", "age": 33 }
string    | `StringType`  | `string()`      | "hello"

All methods that will create these types are defined in `SchemaComponents` class as static methods.

```java
  import static com.github.i49.hibiscus.schema.SchemaComponents.*;
  string(); // creates an instance of string type.
  array();  // creates an instance of array type.
  object(); // creates an instance of object type.
```

### 2.1. Array type

Array type can contain zero or more elements between opening and closing square brackets. An example value of this type is shown below:

```json
  [ "milk", "bread", "eggs" ]
```

Array types can be created as follows:
```java
  /* static import statement omitted */
  array(/* types of array elements here */);
```

The example array shown contains only `string`s as its elements, so you can define the type like below:
```java
  array(string());
```

### 2.2. Object type

Object type can contain zero or more key/value pairs, which are called *properties*, between opening and closing curly brackets. An example value of this type is shown below:

```json
  {
    "name": "John Smith",
    "age": 33
  }
```
Object types can be created as follows:
```java
  /* static import statement omitted */
  object(/* property definitions here */);
```

Each property contained in object type can be created by methods `required()` or `optional()`, those are also provided by `SchemaComponents` class. Properties created by `required()` is mandatory for the object and must exist always in the values of the type. Properties created by `optional()` is not mandatory, therefore may be omitted in some values of the type.

The statement below shows how to create required property:
```java
  required("name", string());
```
The first parameter specifies the name of the property and the second parameter specifies the type of the property, in this case the type is string.  

Optional propety can be created in the same way.
```java
  optional("age", integer());
```

Putting these together, the complete object type is defined as below:
```java
  object(
    required("name", string()),
    optional("age", integer())
  );
```

## 3. Restrictions on types
All types except `null` type can be restricted further by means of various kinds of *facets*. Each facet allows you to limit the value space of the type to which it is applied in its own way.

All currently supported facets are shown in the next table.

facet         |applicable types                        |description
--------------|----------------------------------------|-----------------------------------------------------
`length`      |`string`, `array`                       |accepts only values of a specific length.
`minLength`   |`string`, `array`                       |limits the lower bound of length.
`maxLength`   |`string`, `array`                       |limits the upper bound of length.
`minInclusive`|`number`, `integer`                     |limits the lower bound of values.
`minExclusive`|`number`, `integer`                     |same as `minInclusive` but excludes the bound.
`maxInclusive`|`number`, `integer`                     |limits the upper bound of values.
`maxExclusive`|`number`, `integer`                     |same as `maxInclusive` but excludes the bound.
`pattern`     |`string`                                |restricts string values by a regular expression.
`unique`      |`array`                                 |forces each element of array to be unique.
`enumeration` |`boolean`, `string`, `number`, `integer`|restricts the value space to a set of distinct values.
`assertion`   |all but `null`                          |adds arbitrary assertions on the type.
`format`      |`string`                                |specifies detailed format of the type.

### 3.1. length
The `length` facet allows you to restrict values to have a specific length. It can be applied to `string` and `array` types.

For instance, `string` type below must have exactly 8 characters in the value.
```java
  string().length(8);
```
The following `array` type must exactly 3 elements of `number` type.
```java
  array(number()).length(3);
```

### 3.2. minLength and maxLength
`minLength` and `maxLength` facets allow you to restrict the range of the length of value. They can be applied to `string` and `array` types, as same as `length` facet.

The following `string` type must have at least 8 characters.
```java
  string().minLength(8);
```
The following `array` type must have 3 or 4 elements of `number` type.
```java
  array(number()).minLength(3).maxLength(4);
```

### 3.3. minInclusive, minExclusive, maxInclusive and maxExclusive
`minInclusive` and `minExclusive` facets restrict the lower bound of numeric value.
Similarly, `maxInclusive` and `maxExclusive` facets restrict the upper bound of numeric value.
These facets can be applied to `number` and `integer` types.
Both `minExlusive` and `maxExclusive` are used to exclude the boundaries from the valid range of value.

The following `integer` type accepts only values from 1 to 12.
```java
  integer().minInclusive(1).maxInclusive(12);
```

### 3.4. pattern
`pattern` facet limits the values of the type to the pattern specified by a regular expression.
The regular expression must be the dialect for Java language, not ECMA 262 regular expression for JavaScript.
This facet can be applied only to `string` type.

The following `string` type accepts Social Security number in United States.
```java
  string().pattern("\\d{3}-?\\d{2}-?\\d{4}");
```

### 3.5. unique
`unique` facet can be applied to `array` type.
When this facet is applied to an `array`, each element in the array must have a unique value.

The following property "tags" must have unique values of `string`.
```java
  object(optional("tags", array(string()).unique()));
```

### 3.6. enumeration
`enumeration` facet allows you to restrict the value space of the type to a set of distinct values.
This facet can be applied to `boolean`, `string`, `number`, `integer` types.

The following `string` type accepts "small", "medium" or "large" and rejects any other values.
```java
  string().enumeration("small", "medium", "large");
```

The following `integer` type accepts only 8, 10, and 16..
```java
  integer().enumeration(8, 10, 16);
```

### 3.7. assertion
`assertion` facet is so powerful tool that it allows you to add arbitrary assertions on the type.
This facet can be applied to all types except `null` type.

`assertion` facet receives two lambda expressions as its parameters.
The first one is `Predicate` functional interface that returns `true` if the assertion succeeded and
returns `false` if the assertion failed.
The second parameter supplies a message for the problem to be reported when the assertion failed.

The following `integer` type accepts only even numbers.
```java
  integer().assertion(
    value->((value.intValue() % 2) == 0),
    (value, locale)->"Value must be a even number."
  );
```
In the following `object` type definition, `comment` property is required if value of `rate` property is less than perfect.
```java
  object(
    required("rate", integer().minInclusive(1).maxInclusive(5)),
    optional("comment", string())
  ).assertion(
    (JsonObject value)->{
      if (value.getInt("rate") < 5) {
        return value.containsKey("comment");
      } else {
        return true;
      }
    },
    (value, locale)->"Any comments please."
  );

```

### 3.8. format
`format` facet allows you to specify the detailed format of the type.
All currently supported formats are listed below:
* `datetime`
* `email`
* `hostname`
* `ipv4`
* `ipv6`
* `anyURI`
* `absoluteURI`

These formats can be obtained by the class methods defined in `Formats` class
and can be applied only to `string` type.


The following `string` type accepts values that represent email addresses.
```java
  import static com.github.i49.hibiscus.formats.Formats.*;
  string().format(email());
```
