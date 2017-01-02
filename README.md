# Hibiscus

[![Apache 2.0 License](https://img.shields.io/:license-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0
) [![Build Status](https://travis-ci.org/i49/Hibiscus.svg?branch=master)](https://travis-ci.org/i49/Hibiscus)

Hibiscus is JSON validator that verifies JSON documents against your schema which can be written as Java code.

## 1. Introduction

### 1.1. Why do we write JSON schema in Java?
Writing JSON schema in Java has following advantages for Java developers.

* Your favorite IDE compiles it and detects syntactical errors automatically.
* Code completion will be available.
* Omits parsing schema file at every startup.
* Avoids troubles in missing "$ref" links.
* Can extend validation logic with Java code as much as needed.

### 1.2. Schema example

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

### 1.3. How to write your own JSON validator

1. Create a new class that extends `BasicJsonValidator` class.

  ```java
  import com.github.i49.hibiscus.validation.BasicJsonValidator;

  public class PersonValidator extends BasicJsonValidator {
  }
  ```

2. Add `static import` statement which will make your schema building easier.

  ```java
  import static com.github.i49.hibiscus.schema.SchemaComponents.*;
  ```

3. Define your schema as a class constant of your validator.

  ```java
  import com.github.i49.hibiscus.validation.BasicJsonValidator;
  import com.github.i49.hibiscus.schema.Schema;
  import static com.github.i49.hibiscus.schema.SchemaComponents.*;

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
  import static com.github.i49.hibiscus.schema.SchemaComponents.*;

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

### 1.4. How to validate JSON documents with your validator

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

## 2. Documentation

For more details about this library, please see following documents.

* [Schema Basics](hibiscus-doc/01_schema-basics.md) which show you how to write schema
* [Javadoc API Reference](https://i49.github.io/Hibiscus/apidocs/index.html)

## 3. Installation

All prerequisites in order to build Hibiscus are listed below:

* JDK 8
* Apache Maven 3.3.9

The artifact can be built and installed into your local Maven repository as follows.

```bash
$ git clone https://github.com/i49/Hibiscus.git
$ cd hibiscus
$ mvn install
```

Please add a dependency to pom.xml of your application in order to use this library.

```xml
<dependency>
  <groupId>com.github.i49</groupId>
  <artifactId>hibiscus</artifactId>
  <version>0.1.0</version>
</dependency>
```

Hibiscus requires at runtime one of the implementations of [Java API for JSON Processing](http://json-processing-spec.java.net/).
It is currently tested with the API implementations listed below.

* [Reference Implementation](https://jsonp.java.net/), provided by glassfish.org
* [Apache Johnzon](https://johnzon.apache.org/)

You need to add one of the implementations as a dependency to your pom.xml in addition to Hibiscus iteself.
If your choice is the Reference Implementation, you can specify it in your pom.xml as listed below.

```xml
<dependency>
  <groupId>org.glassfish</groupId>
  <artifactId>javax.json</artifactId>
  <version>1.0.4</version>
</dependency>
```

In the case that your choice is Apache Johnzon, you can specify it as follows.

```xml
<dependency>
  <groupId>org.apache.johnzon</groupId>
  <artifactId>johnzon-core</artifactId>
  <version>1.0.0</version>
</dependency>
```

## 4. Validator examples

Some examples of validators are available in [hibiscus-examples](https://github.com/i49/Hibiscus/tree/master/hibiscus-examples).

## 5. And other solutions

If you prefer programming language agnostic approach, [JSON Schema](http://json-schema.org/) is the way to go.
But I have a belief that it is preferable to write schema by internal DSL using host language, especially for the validation purpose.
