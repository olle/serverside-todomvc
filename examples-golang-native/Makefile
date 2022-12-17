.PHONY: check build clean test run-test all

all:
	go run .

SELF := $(shell basename $(shell pwd))
URL=http://localhost:8080

banner:
	@echo '----------------------------------------------------------------------'
	@echo '# ${SELF}'

check: banner check-go

check-%:
	@echo -n '--> Checking if $* is installed... '
	@(which $* > /dev/null && echo '[OK]') || (echo '[MISSING]'; exit 1)

build:
	@echo -n '--> Building ${SELF} '
	@go build > /dev/null 2>&1 && echo ' [DONE]'

clean:
	@echo -n '--> Cleaning ${SELF} '
	@go clean > /dev/null 2>&1 && echo ' [DONE]'

test:
	@echo '--> Running tests against ${URL} ...'
	@go test 2> /dev/null && echo '--> Testing ${SELF}  [DONE]'

run-test:
	@$(MAKE) URL=${URL} -C ../ run-test
