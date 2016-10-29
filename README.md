# Hibiscus
Hibiscus is JSON schema language defined by Java constructs.

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
If you want to validate this JSON against adequate schema, we can write the schema in Java like following:

```java
ObjectType schema = object(
    required("firstName", string()),
    required("lastName", string()),
    optional("age", integer()),
    optional("hobbies", array(string()))
);
```

## How to write your own JSON validator

1. Create class extending `JsonValidator` class.

   ```java
   import com.github.i49.hibiscus.validation.JsonValidator;

   public class YourValidator extends JsonValidator {
   }
   ```

2. Add `static import` that will help building schema.

   ```java
   import static com.github.i49.hibiscus.validation.SchemaComponents.*;
   ```

3. Define your schema as class variable of your validator.

   ```java
   import com.github.i49.hibiscus.validation.JsonValidator;
   import com.github.i49.hibiscus.validation.ObjectType;
   import static com.github.i49.hibiscus.validation.SchemaComponents.*;

   public class PersonValidator extends JsonValidator {
       private static final ObjectType schema = object(
           required("firstName", string()),
           required("lastName", string()),
           optional("age", integer()),
           optional("hobbies", array(string()))
       );
   }
   ```  
4. Pass the schema to constructor of super class and complete it.

   ```java
   import com.github.i49.hibiscus.validation.JsonValidator;
   import com.github.i49.hibiscus.validation.ObjectType;
   import static com.github.i49.hibiscus.validation.SchemaComponents.*;

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
       System.out.println(p.getMessage());
   }
   ```

3. Use parsed JSON value.

   ```java
   JsonValue root = result.getValue();
   ```
   
   Hibiscus does both reading and validating at the same time.
   It returns JSON primitives defined in [JSON Processing (JSR-353, JSON-P)](http://json-processing-spec.java.net/) even when schema validation found some problems.

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
