# Schema Basics

## 1. Basic types

Hibiscus offers following JSON types that can compose the schema.

name      | class         | creation method | values accepted
----------|---------------|-----------------|----------------------------------------
array     | `ArrayType`   | `array()`       | [1, 2, 3]
boolean   | `BooleanType` | `bool()`        | true
integer   | `IntegerType` | `integer()`     | 42
number    | `NumberType`  | `number()`      | 3.14
null      | `NullType`    | `nil()`         | null
object    | `ObjectType`  | `object()`      | { "name": "John", "age": 33 }
string    | `StringType`  | `string()`      | "hello"

All methods that will create these types are defined in `JsonTypes` class as static methods.

```java
  import static com.github.i49.hibiscus.schena.JsonTypes.*;
  string(); // this creates an instance of string type.
```

### 1.1. Array type

Array type can contain zero or more elements between opening and closing square brackets. An example value of this type is shown below:

```json
  [ "milk", "bread", "eggs" ]
```

Array types can be created as follows:
```java
  /* static import statement omitted */
  array(/* types of elements will come later */);
```

The example array shown contains only strings as its elements, so you can define the type like below:
```java
  array(string());
```

### 1.2. Object type

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
  object(/* property definitions will come later */);
```

Each property contained in object type can be created by methods `required()` or `optional()`, those are provided by `JsonTypes` class also. Properties created by `required()` is mandatory for the object and must exist always in the values of the type. Properties created by `optional()` is not mandatory, therefore may be omitted in some values of the type.

The statement below shows how to create required property:
```java
  required("name", string()),
```
The first parameter specifies the name of the property and the second parameter specifies the type of the property, in this case the type is string.  

Optional propety can be created in the same way.
```java
  optional("age", integer()),
```

Putting these together, the complete object type is defined as below:
```java
  object(
    required("name", string()),
    optional("age", integer())
  );
```

## 2. Restrictions on types
All types except null type can be restricted by various kind of *facets*. Each facets will constrain the value space of the type to which it is applied.

continued...
