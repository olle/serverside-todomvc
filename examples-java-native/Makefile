.PHONY: start check build clean test run-test all

SELF=examples-java-spring-mvc-thymeleaf
URL=http://localhost:8080/${SELF}

start:
	./mvnw clean package
	java -jar target/examples-java-native.jar

banner:
	@echo '----------------------------------------------------------------------'
	@echo '# ${SELF}'
	@echo '----------------------------------------------------------------------'

check: banner check-java

check-%:
	@echo -n '--> Checking if $* is installed... '
	@(which $* > /dev/null && echo '[OK]') || (echo '[MISSING]'; exit 1)

build:
	@echo -n '--> Building ${SELF} '
	@./mvnw verify -q > /dev/null 2>&1 && echo ' [DONE]'

clean:
	@echo -n '--> Cleaning ${SELF} '
	@./mvnw clean -q > /dev/null 2>&1 && echo ' [DONE]'

test:
	@echo '--> Running tests against ${URL} ...'
	@./mvnw -Pit -q 2> /dev/null && echo '--> Testing ${SELF}  [DONE]'

run-test:
	@$(MAKE) URL=${URL} -C ../ run-test

all: clean build test
