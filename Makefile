.PHONY: help setup all java-examples run-tests

## Helper target that displays some information.
help:
	@echo "\n Hi, I'm make! I'll make things for you.\n"
	@echo "    'make setup' will help you install the tools you need for development."
	@echo "    'make all' will build and test all the examples in the project."
	@echo "\n Bye, happy hacking!\n"

## Setup target, helping out with the installation of some tools.
setup:
	@echo " Let's see if you have Node.js and npm installed?"
	@which npm || (echo " Ouch, too bad. Start by installing that first please."; exit 1)
	@echo " Oh, great."
	@echo "\n Now let's install [jquerygo] with it's dependencies...."
	@npm install jquerygo
	@echo "\n Now let's install [async] "
	@npm install async
	@echo "\n Good, all done. Now move along!"

## Build __all__ the examples!
all: \
java-examples \
run-tests \

## Builds the Java based examples using Maven
java-examples:
	@echo "BUILDING ALL JAVA EXAMPLES"
	@echo "=========================="
	@mvn -f java-examples/pom.xml clean verify

run-tests:
	@echo "PREPARING TESTS"
	@echo "==============="
	@echo " Do preparation stuff here..."
