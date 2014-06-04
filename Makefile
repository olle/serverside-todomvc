###
### Hi, this is the big fat `Makefile` of the Server-side TodoMVC project.
###
### General design is that we can `build` and `test`. Building is supposed
### to create a packaged artifact, that can be run using some type of web
### server. Testing is aimed at asserting or validating the completeness of
### a built example, by running some high level web-test on it.
###
### Also, my Makefile-foo is not so strong, so bare with me. Any
### improvements are welcome.
###
.PHONY: help setup all examples clean

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
	@echo "\n Now let's make sure zombie is installed...\n"
	@npm update zombie
	@echo "\nGood!\n Now let's make sure mocha is installed...\n"
	@npm update mocha
	@echo "\nGood!\n Now let's make sure assert is installed...\n"
	@npm update assert
	@echo "\nVery good!\n All done. Now move along!\n"

## Build __all__ the examples!
all: examples

## Build the examples
examples:
	$(MAKE) -C serverside-todomvc-java-servlet
	$(MAKE) -C serverside-todomvc-java-wicket

## Clean out all the examples, duh!
clean:
	$(MAKE) -C serverside-todomvc-java-servlet $@
	$(MAKE) -C serverside-todomvc-java-wicket $@

## TODO: This should be considered again, perhaps it's better
##       to create explicit test setup too?
## Run acceptance tests aginst some URL
##test:
##	@env URL=${URL} ./node_modules/.bin/mocha --reporter list
