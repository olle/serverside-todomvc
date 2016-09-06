.PHONY: check build clean test run-test all

SELF=examples-python-flask
URL=http://127.0.0.1:8080

banner:
	@echo '----------------------------------------------------------------------'
	@echo '# ${SELF}'
	@echo '----------------------------------------------------------------------'

check: banner check-python check-pip check-virtualenv

check-%:
	@echo -n '--> Checking if $* is installed... '
	@(which $* > /dev/null && echo '[OK]') || (echo '[MISSING]'; exit 1)

build:
	@echo '--> Building ${SELF}  [DONE]'

clean:
	@echo '--> Cleaning ${SELF}  [DONE]'

test:
	@echo '--> Running tests against ${URL} ...'
	@./it.sh && echo '--> Testing ${SELF}  [DONE]'

run-test:
	@$(MAKE) URL=${URL} -C ../ run-test

all: banner clean build test
