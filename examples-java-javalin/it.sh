#!/bin/sh

java -jar target/examples-java-javalin-1.0.0-SNAPSHOT.jar > /dev/null 2>&1 &
PID=$!
sleep 5
make run-test
kill ${PID}
