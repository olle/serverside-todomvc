#!/bin/sh

java -jar target/examples-java-native.jar > /dev/null 2>&1 &
PID=$!
sleep 5
make run-test
kill ${PID}
