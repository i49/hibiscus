# Hibiscus
Hibiscus is JSON schema language defined by Java constructs.

## Why do we write JSON schema in Java?
Writing JSON schema in Java has following merits:

* Your favorite IDE compiles it and detects errors automatically.
* Code completion available.
* Omits parsing external schema resource at every startup.
* Avoid troubles in missing "$ref" links.
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

TODO

## How to execute validation

TODO

## How to build

```bash
$ git clone https://github.com/i49/Hibiscus.git
$ cd hibiscus
$ mvn install
```

## Library dependencies

Hibiscus depends on [Java API for JSON Processing (JSR-353)](http://json-processing-spec.java.net/) at runtime. It is necessary to include dependency to one of implementation into your application besides Hibiscus itself. Reference implementation is available as follows:

```xml
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.json</artifactId>
    <version>1.0.4</version>
</dependency>
```
