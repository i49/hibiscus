# Hibiscus

[![Build Status](https://travis-ci.org/i49/Hibiscus.svg?branch=master)](https://travis-ci.org/i49/Hibiscus)

Hibiscus is JSON validator against schema defined by Java-based DSL. 

## Why do we write JSON schema in Java?
Writing JSON schema in Java has following merits:

* Your favorite IDE compiles it and detects errors automatically.
* Code completion available.
* Omits parsing external schema resource at every startup.
* Avoids troubles in missing "$ref" links.
* Can extend validation logic with Java code.

## Schema example

As first example we assume that you have following JSON contents:

```json
{
  "firstName": "Jason",
  "lastName": "Bourne",
  "age": 46,
  "hobbies": ["shooting", "recollection"]
}
```

If you want to validate this JSON against appropriate schema, we can write the schema as Java codes like following:

```java
ObjectType schema = object(
  required("firstName", string()),
  required("lastName", string()),
  optional("age", integer()),
  optional("hobbies", array(string()))
);
```

## How to write your own JSON validator

1. Create a class extending `JsonValidator` class.

  ```java
  import com.github.i49.hibiscus.validation.JsonValidator;

  public class PersonValidator extends JsonValidator {
  }
  ```

2. Add `static import` statement that will help your schema building.

  ```java
  import static com.github.i49.hibiscus.schema.types.SchemaComponents.*;
  ```

3. Define your schema as a class variable of your validator class.

  ```java
  import com.github.i49.hibiscus.validation.JsonValidator;
  import com.github.i49.hibiscus.schema.types.ObjectType;
  import static com.github.i49.hibiscus.schema.types.SchemaComponents.*;

  public class PersonValidator extends JsonValidator {
    private static final ObjectType schema = object(
      required("firstName", string()),
      required("lastName", string()),
      optional("age", integer()),
      optional("hobbies", array(string()))
    );
  }
  ```  
4. Pass the schema to constructor of superclass and then your work is done.

  ```java
  import com.github.i49.hibiscus.validation.JsonValidator;
  import com.github.i49.hibiscus.schema.types.ObjectType;
  import static com.github.i49.hibiscus.schema.types.SchemaComponents.*;

  public class PersonValidator extends JsonValidator {
    private static final ObjectType schema = object(
      required("firstName", string()),
      required("lastName", string()),
      optional("age", integer()),
      optional("hobbies", array(string()))
    );

    public PersonValidator() {
      super(schema)
    }
  }
  ```

## How to validate JSON with your validator

1. Validate JSON with your validator.

  ```java
  PersonValidator validator = new PersonValidator();
  ValidationResult result = null;
  try (Reader reader = new FileReader("person.json")) {
    result = validator.validate(reader);
  }
  ```

2. Handle detected problems properly.

  ```java
  for (Problem p: result.getProblems()) {
    // handles each problem here.
    // We just print text representation of the problem here.
    System.out.println(p);
  }
  ```

 One of nice features of Hibiscus is that it reports *where* these problems occurred at
 such as line number and column number. This can be accomplished because the library do
 both loading and validating JSON content at the same time, not after completely loading it.

3. Use loaded JSON value as you like in your application.

  ```java
  JsonValue root = result.getValue();
  ```
   
   Hibiscus returns JSON primitive values defined in [Java API for JSON Processing (JSR-353, JSON-P)](http://json-processing-spec.java.net/).
   Please note it returns value even when the JSON content does not satisfy the given schema.

## How to build

```bash
$ git clone https://github.com/i49/Hibiscus.git
$ cd hibiscus
$ mvn install
```

## Library dependencies

Hibiscus depends on [Java API for JSON Processing](http://json-processing-spec.java.net/) at runtime. It is necessary to include dependency to one of implementation into your application besides Hibiscus itself. Reference implementation is available as follows:

```xml
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.json</artifactId>
    <version>1.0.4</version>
</dependency>
```

## Validator examples

Examples of validators are available in [hibiscus-examples](https://github.com/i49/Hibiscus/tree/master/hibiscus-examples).

