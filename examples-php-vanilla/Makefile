.PHONY: build clean test run-test all

SELF=examples-php-vanilla
URL=http://127.0.0.1:8080

build:
	@echo '--> Building ${SELF}  [DONE]'

clean:
	@echo '--> Cleaning ${SELF}  [DONE]'

test:
	@echo '--> Running tests against ${URL} ...'
	@./it.sh && echo '--> Testing ${SELF}  [DONE]'

run-test:
	@$(MAKE) URL=${URL} -C ../ run-test

all: clean build test
