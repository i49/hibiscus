# Hibiscus

[![Build Status](https://travis-ci.org/i49/Hibiscus.svg?branch=master)](https://travis-ci.org/i49/Hibiscus)

Hibiscus is JSON validator which verifies that JSON content conforms to schema defined by Java-based DSL. 

## Why do we write JSON schema in Java?
Writing JSON schema in Java has following merits:

* Your favorite IDE compiles it and detects errors automatically.
* Code completion available.
* Omits parsing external schema resource at every startup.
* Avoids troubles in missing "$ref" links.
* Can extend validation logic with Java code.

## Schema example

As first example we assume that you have following JSON content:

```json
{
  "firstName": "Jason",
  "lastName": "Bourne",
  "age": 46,
  "hobbies": ["shooting", "recollection"]
}
```

If you would like to validate this JSON against appropriate schema, we can write the schema as Java code like following:

```java
ObjectType schema = object(
  required("firstName", string()),
  required("lastName", string()),
  optional("age", integer()),
  optional("hobbies", array(string()))
);
```

## How to write your own JSON validator

1. Create a new class that extends `BasicJsonValidator` class.

  ```java
  import com.github.i49.hibiscus.validation.BasicJsonValidator;

  public class PersonValidator extends BasicJsonValidator {
  }
  ```

2. Add `static import` statement which will make your schema building easy.

  ```java
  import static com.github.i49.hibiscus.schema.JsonTypes.*;
  ```

3. Define your schema as a class variable of your validator.

  ```java
  import com.github.i49.hibiscus.validation.BasicJsonValidator;
  import com.github.i49.hibiscus.schema.ObjectType;
  import static com.github.i49.hibiscus.schema.JsonTypes.*;

  public class PersonValidator extends BasicJsonValidator {
    private static final ObjectType schema = object(
      required("firstName", string()),
      required("lastName", string()),
      optional("age", integer()),
      optional("hobbies", array(string()))
    );
  }
  ```  
4. Pass the schema object to the constructor of superclass, and then your work is done.

  ```java
  import com.github.i49.hibiscus.validation.BasicJsonValidator;
  import com.github.i49.hibiscus.schema.ObjectType;
  import static com.github.i49.hibiscus.schema.JsonTypes.*;

  public class PersonValidator extends BasicJsonValidator {
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

## How to validate JSON document with your validator

1. Validate JSON document with your validator.

  ```java
  // Instantiates your validator.
  PersonValidator validator = new PersonValidator();
  // An object to retrieve validation result.
  ValidationResult result = null;
  try (Reader reader = new FileReader("person.json")) {
    // Reads and validates JSON document here.
    result = validator.validate(reader);
  }
  ```

2. Process detected problems properly.

  ```java
  for (Problem problem: result.getProblems()) {
    // Handles each problem here.
    // We just print text representation of the problem here.
    System.out.println(problem);
  }
  ```

 One of the nice features of Hibiscus is that it reports *where* these problems occurred,
 such as line number and column number. This can be accomplished because the library do
 both loading and validating JSON document at the same time, not after completely loading it.

3. Make use of retrieved JSON value as you like in your application.

  ```java
  JsonValue root = result.getValue();
  ```
   
   Hibiscus returns JSON primitive values defined in [Java API for JSON Processing (JSR-353, JSON-P)](http://json-processing-spec.java.net/).
   Please note it returns value even when the JSON document does not satisfy the given schema.

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

