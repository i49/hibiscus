#!/bin/bash

cd hibiscus
mvn install -Dmaven.javadoc.skip=true
cd ../hibiscus-examples
mvn package
java -jar target/hibiscus-examples-0.1.0-SNAPSHOT.jar
