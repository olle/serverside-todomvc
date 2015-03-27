#!/bin/sh

java -jar target/examples-java-spark-mustache.jar > /dev/null 2>&1 &
PID=$!
make run-test
kill ${PID}
