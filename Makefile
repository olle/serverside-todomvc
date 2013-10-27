.PHONY: all java-examples

all: java-examples

java-examples:
	@echo "BUILDING JAVA EXAMPLES"
	@echo "======================"
	@mvn -f java-examples/pom.xml clean verify

