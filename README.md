# Hibiscus

[![Build Status](https://travis-ci.org/i49/Hibiscus.svg?branch=master)](https://travis-ci.org/i49/Hibiscus)

Hibiscus is JSON validator that verifies JSON documents against your schema which can be written as Java code.

## Why do we write JSON schema in Java?
Writing JSON schema in Java has following advantages:

* Your favorite IDE compiles it and detects syntactical errors automatically.
* Code completion will be available.
* Omits parsing schema file at every startup.
* Avoids troubles in missing "$ref" links.
* Can extend validation logic with Java code as much as needed.

## Schema example

As first example we assume that you have JSON document below that you would like to validate in your application.

```json
{
  "firstName": "Jason",
  "lastName": "Bourne",
  "age": 46,
  "hobbies": ["shooting", "recollection"]
}
```

Supposing that the first two properties of the object are mandatory and others are optional.
We can write the schema for this kind of document as Java code like following:

```java
Schema schema = schema(
  object(
    required("firstName", string()),
    required("lastName", string()),
    optional("age", integer()),
    optional("hobbies", array(string()))
  )  
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

3. Define your schema as a class constant of your validator.

  ```java
  import com.github.i49.hibiscus.validation.BasicJsonValidator;
  import com.github.i49.hibiscus.schema.Schema;
  import static com.github.i49.hibiscus.schema.JsonTypes.*;

  public class PersonValidator extends BasicJsonValidator {
    // Schema definition.
    private static final Schema schema = schema(
      object(
        required("firstName", string()),
        required("lastName", string()),
        optional("age", integer()),
        optional("hobbies", array(string()))
      )
    );  
  }
  ```  
4. Pass the schema to the constructor of superclass, and then your work is done.

  ```java
  import com.github.i49.hibiscus.validation.BasicJsonValidator;
  import com.github.i49.hibiscus.schema.Schema;
  import static com.github.i49.hibiscus.schema.JsonTypes.*;

  public class PersonValidator extends BasicJsonValidator {
    // Schema definition.
    private static final Schema schema = schema(
      object(
        required("firstName", string()),
        required("lastName", string()),
        optional("age", integer()),
        optional("hobbies", array(string()))
      )
    );  

    public PersonValidator() {
      super(schema)
    }
  }
  ```

## How to validate JSON documents with your validator

1. Create an instance of your validator.

  ```java
  PersonValidator validator = new PersonValidator();
  ```

2. Validate JSON document with the validator.

  ```java
  // An object to retrieve validation result.
  ValidationResult result = null;
  try (Reader reader = new FileReader("person.json")) {
    // Reads and validates JSON document here.
    result = validator.validate(reader);
  }
  ```

3. Process detected problems properly.

  ```java
  for (Problem problem: result.getProblems()) {
    // Handles each problem here.
    // We just print text representation of the problem here.
    System.out.println(problem);
  }
  ```

 One of the nice features of Hibiscus is that it reports *where* these problems occurred,
 such as line and column numbers. This can be accomplished because the library do
 both loading and validating JSON document at the same time, not after completely loading it
 and building a tree of JSON values.

4. Make use of retrieved JSON value as you like in your application.

  ```java
  JsonValue root = result.getValue();
  ```

   Hibiscus returns JSON primitive values defined in [Java API for JSON Processing (JSR-353, JSON-P)](http://json-processing-spec.java.net/).
   Please note that it returns JSON value even when the JSON document does not obey the given schema, as long as the document is *well-formed* and not broken as JSON.

## Schema basics

Please see the document for [Schema Basics](https://github.com/i49/Hibiscus/tree/master/schema-basics.md). 

## Installation

```bash
$ git clone https://github.com/i49/Hibiscus.git
$ cd hibiscus
$ mvn install
```

## Library dependencies

Hibiscus requires at runtime one of the API implementations of [Java API for JSON Processing](http://json-processing-spec.java.net/). In addition to Hibiscus itself, you need to add the dependency to your application explicitly. If your choice is the reference implementation offered by jsonp project hosted on [java.net](http://java.net), you can specify it in your pom.xml as follows.   

```xml
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.json</artifactId>
    <version>1.0.4</version>
</dependency>
```

## Validator examples

Some examples of validators are available in [hibiscus-examples](https://github.com/i49/Hibiscus/tree/master/hibiscus-examples).

## And other solutions

If you prefer programming language agnostic approach, [JSON Schema](http://json-schema.org/) is the way to go.
But I have a belief that it is preferable to write schema by internal DSL using host language, especially for the validation purpose.
