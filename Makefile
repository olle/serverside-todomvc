###
### Hi, this is the big fat `Makefile` of the Server-side TodoMVC project.
###
### General design is that we can `build` and `test`. Building is supposed
### to create a packaged artifact, that can be run using some type of web
### server. Testing is aimed at asserting or validating the completeness of
### a built example, by running some high level web-test on it.
###
### Also, my Makefile-foo is not so strong, so bare with me. Any improvements
### are welcome.
###
.PHONY: help setup all build clean test run-test

## Helper target that displays some information.
help:
	@echo ""
	@echo " Server-Side TodoMVC - Makefile targets"
	@echo " ======================================="
	@echo ""
	@echo " Top-level targets:"
	@echo "   setup      Install/check required tools for development"
	@echo "   all        Build all examples (equivalent to 'make build')"
	@echo "   build      Build all examples"
	@echo "   test       Run acceptance tests against built examples (requires running instances)"
	@echo "   clean      Clean build artifacts for all examples"
	@echo "   check      Check required tools for all examples"
	@echo "   run-test   Run tests from root (sets up test harness)"
	@echo "   help       Show this help message"
	@echo ""
	@echo " Per-example targets (run from example directory):"
	@echo "   make build - Compile/package the example"
	@echo "   make test  - Start app, run E2E tests, stop app"
	@echo "   make clean - Remove build artifacts"
	@echo "   make check - Check required tools"
	@echo ""
	@echo " Examples:"
	@echo "   make build                    # Build everything"
	@echo "   make -C examples-java-servlet build"
	@echo "   make -C examples-python-flask test"
	@echo ""
	@echo " Happy hacking!"
	@echo ""

## Setup target, helping out with the installation of some tools.
setup: which-node which-npm check run-setup

which-%:
	@echo -n '--> Checking if $* is installed... '
	@(which $* > /dev/null && echo '[OK]') || (echo '[MISSING]'; exit 1)

##
## All examples must support the targets `build`, `test` and `clean`.
##
##   `build` - assumes the example is complied, unit tested, packaged and - for
##             that specific language or framework - prepared in such a way that
##             it can be locally run, serving the web app.
##
##   `test`  - will expect the example to start the web app, allow for end to
##             end tests to be run, and then stop the app again.
##
##   `clean` - assumes the example removes any temporarily built artefacts, and
##             ensures the project is source-only, if possible.
##
check build test clean:
	@$(MAKE) -C examples-golang-native $@ 2>/dev/null || true
	@$(MAKE) -C examples-java-native $@ 2>/dev/null || true
	@$(MAKE) -C examples-java-ninjaframework $@ 2>/dev/null || true
	@$(MAKE) -C examples-java-servlet $@ 2>/dev/null || true
	@$(MAKE) -C examples-java-spark-mustache $@ 2>/dev/null || true
	@$(MAKE) -C examples-java-spring-boot-freemarker $@ 2>/dev/null || true
	@$(MAKE) -C examples-java-spring-boot-mustache $@ 2>/dev/null || true
	@$(MAKE) -C examples-java-spring-boot-thymeleaf $@ 2>/dev/null || true
	@$(MAKE) -C examples-java-wicket $@ 2>/dev/null || true
	@$(MAKE) -C examples-java-javalin $@ 2>/dev/null || true
	@$(MAKE) -C examples-kotlin-simple $@ 2>/dev/null || true
	@$(MAKE) -C examples-php-vanilla $@ 2>/dev/null || true
	@$(MAKE) -C examples-python-flask $@ 2>/dev/null || true
	@echo '----------------------------------------------------------------------'

run-setup:
	@echo " Everyting seems to be in order."
##	@echo " Let's see if you have Node.js and npm installed?"
##	@which npm || (echo " Ouch, too bad. Start by installing that first please."; exit 1)
##	@echo " Oh, great."
##	@echo "\n Now let's make sure everything is installed properly...\n"
##	@npm install
##	@echo "\nVery good!\n All done. Now move along!\n"

## Build __all__ the examples!
all: build

## Executes the acceptance tests, assuming a correct ${URL} is set
run-test:
	@npm test -s
