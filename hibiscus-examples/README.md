# Hibiscus Examples

## How to build examples

As prerequisites you need to build and install the main artifact of Hibiscus properly before jumping to the examples here. After the task is done you can type commands to build the examples as follows.   

```bash
$ cd <this directory>
$ mvn package
```

## Running examples

Running all examples is easy to just start off runnable jar located in target directory.

```bash
$ java -jar target/hibiscus-examples-<version>.jar
```

## Summary of examples

### 1. HelloValidator

The starting example should be the one to deliver famous greeting.

### 2. PersonValidator

Basic example to validate JSON document that contains only one JSON object which represents personal profile.

### 3. ProductsValidator

Example to validate JSON document that has JSON array at its root.

Notice: the original JSON document of this example is available at http://json-schema.org/ and licensed under the AFL or BSD license.  

### 4. PedigreeValidator

Demonstration of recursive schema definition.
