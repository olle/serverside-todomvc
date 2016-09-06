#!/bin/sh

mvn ninja:run > /dev/null 2>&1 &
PID=$!
sleep 5
make run-test
kill ${PID}
