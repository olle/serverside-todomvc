#!/bin/sh

virtualenv .
. bin/activate
pip install -r requirements.txt
python todomvc.py > /dev/null 2>&1 &
PID=$!
make run-test && kill ${PID} || kill ${PID}
