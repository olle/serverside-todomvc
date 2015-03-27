#!/bin/sh

php -S 127.0.0.1:8080 > /dev/null 2>&1 &
PID=$!
make run-test
kill ${PID}
