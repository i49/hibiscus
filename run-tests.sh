#!/bin/bash

cd hibiscus
mvn install
cd ../hibiscus-examples
mvn package
java -jar target/hibiscus-examples-0.1.0.jar
