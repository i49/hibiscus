# Change Log

## 0.3.0 (2017-01-14)

**Enhancements:**
* Every problem found while validation now returns the value that caused the problem and the JSON pointer that refers the value.
* Added another pattern() method in SchemaComponents class that allows you to specify property names by string Predicate objects or any Format objects such as email or anyURI.

**Fixed bugs:**
* Fixed bug that array element problem returns wrong value which caused the problem.

## 0.2.0 (2017-01-08)

* `ValidationResult.getValue()` now returns JSON values purely instantiated by JSON-P implementation. They do not include objects implemented by this library.

## 0.1.0 (2017-01-01)

* First public release.
