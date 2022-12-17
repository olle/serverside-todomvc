##
## This is the Makefile template that all example implementations must use, and
## adapt to suite the language and framework.
##
## These targets are required to support, as they are implicitly called from the
## root Makefile when doing setup and project checks, build, test and cleaning.
##
## `check` - check if language and tools to build the example are installed and
##           correct. The target should `exit 1` in case of any problems.
##
## `build` - must support full build of the example, including any compilation
##           unit-testing or verifications, as well as ensuring that packaging
##           or preparations are enough in order to start the web app locally.
##
## `test`  - must ensure to provide, from the built artefact, the capability to
##           do full end-2-end testing on the example. This means starting the
##           built web app, yielding to the e2e tests, and then stopping the
##           application.
##
## `clean` - should clean up any built artefacts and try to keep the example
##           source-only, removing temporary files or any output.
##
.PHONY: check build test clean

LANG := java
SELF := $(shell basename $(shell pwd))
URL := http://localhost:8080

banner:
	@echo '----------------------------------------------------------------------'
	@echo '## ${SELF}'

check: banner check-${LANG}

check-%:
	@echo -n '--> Checking if $* is installed... '
	@(which $* > /dev/null && echo '[OK]') || (echo '[MISSING]'; exit 1)

build:
	@echo -n '--> Building ${SELF} '
## TODO: Provide steps to build
	@XXXX build > /dev/null 2>&1 && echo ' [DONE]'

clean:
	@echo -n '--> Cleaning ${SELF} '
## TODO: Provide steps to clean/clear
	@XXXX clean > /dev/null 2>&1 && echo ' [DONE]'

test:
	@echo '--> Running tests against ${URL} ...'
## TODO: Provide steps to start the web app, yield to the run-test and then stop
	@XXXX start/pre 2> /dev/null && echo '--> Started ${SELF}'
	@$(MAKE) URL=${URL} -C ../ run-test
	@XXXX stop/post 2> /dev/null && echo '--> Finished ${SELF}  [DONE]'
