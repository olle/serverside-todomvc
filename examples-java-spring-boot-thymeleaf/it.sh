#!/bin/sh

JAR=$(find target -name "*.jar" | grep -v sources | head -1)
java -jar $JAR > /dev/null 2>&1 &
PID=$!
sleep 10
make run-test
kill ${PID}
