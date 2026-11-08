#!/bin/sh

java -jar target/examples-java-jetty-mustache-jar-with-dependencies.jar > /dev/null 2>&1 &
PID=$!
make run-test
kill ${PID}
