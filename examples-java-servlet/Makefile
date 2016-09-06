.PHONY: check build clean test run-test all

SELF=examples-java-servlet
URL=http://localhost:8080/${SELF}

banner:
	@echo '----------------------------------------------------------------------'
	@echo '# ${SELF}'
	@echo '----------------------------------------------------------------------'

check: banner check-java check-mvn

check-%:
	@echo -n '--> Checking if $* is installed... '
	@(which $* > /dev/null && echo '[OK]') || (echo '[MISSING]'; exit 1)

build:
	@echo -n '--> Building ${SELF} '
	@mvn verify -q && echo ' [DONE]'

clean:
	@echo -n '--> Cleaning ${SELF} '
	@mvn clean -q && echo ' [DONE]'

test:
	@echo '--> Running tests against ${URL} ...'
	@mvn -Pit -q 2> /dev/null && echo '--> Testing ${SELF}  [DONE]'

run-test:
	@$(MAKE) URL=${URL} -C ../ run-test

all: clean build test
