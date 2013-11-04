.PHONY: help setup all java-examples

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
	@echo " Oh, great. So we'll install bower now, since it's required. This could take while actually..."
	@npm install bower
	@echo " Good, all done. Now move along!"

## Build __all__ the examples!
all: java-examples

## Builds the Java based examples using Maven
java-examples:
	@echo "BUILDING JAVA EXAMPLES"
	@echo "======================"
	@mvn -f java-examples/pom.xml clean verify

