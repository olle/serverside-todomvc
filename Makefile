.PHONY: help setup all java-examples test

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
	@echo "\n Now let's install [zombie]\n"
	@npm install zombie
	@echo "\n Now let's install [mocha]\n"
	@npm install mocha
	@echo "\n Now let's install [assert]\n"
	@npm install assert
	@echo "\n Good, all done. Now move along!\n"

## Build __all__ the examples!
all: \
java-examples \
test

## Builds the Java based examples using Maven
java-examples:
	@echo "BUILDING ALL JAVA EXAMPLES"
	@echo "=========================="
	@mvn -f java-examples/pom.xml clean verify

test:
	@echo "RUNNING TESTS"
	@echo "==============="
	@./node_modules/.bin/mocha --reporter list
