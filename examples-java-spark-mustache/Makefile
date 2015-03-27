.PHONY: build clean test run-test all

SELF=examples-java-spark-mustache
URL=http://localhost:4567/

build:
	@echo -n '--> Building ${SELF} '
	@mvn verify -q > /dev/null 2>&1 && echo ' [DONE]'

clean:
	@echo -n '--> Cleaning ${SELF} '
	@mvn clean -q > /dev/null 2>&1 && echo ' [DONE]'

test: build
	@echo '--> Running tests against ${URL} ...'
	@./it.sh && echo '--> Testing ${SELF}  [DONE]'

run-test:
	@$(MAKE) URL=${URL} -C ../ run-test

all: clean build test
