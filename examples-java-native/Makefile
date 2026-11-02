.PHONY: setup start check build clean test run-test all

SELF=examples-java-native
URL=http://localhost:8989/
MISE_EXEC=mise exec --

setup:
	@echo '--> Installing tooling...'
	@mise install

start:
	@$(MISE_EXEC) mvn package -q
	@$(MISE_EXEC) java -jar target/examples-java-native.jar

banner:
	@echo '----------------------------------------------------------------------'
	@echo '# ${SELF}'
	@echo '----------------------------------------------------------------------'

check: banner check-mise

check-%:
	@echo -n '--> Checking if $* is installed... '
	@(which $* > /dev/null && echo '[OK]') || (echo '[MISSING]'; exit 1)

build:
	@echo -n '--> Building ${SELF} '
	@$(MISE_EXEC) mvn package -q > /dev/null 2>&1 && echo ' [DONE]'

clean:
	@echo -n '--> Cleaning ${SELF} '
	@$(MISE_EXEC) mvn clean -q > /dev/null 2>&1 && echo ' [DONE]'

test:
	@echo '--> Running tests against ${URL} ...'
	@$(MISE_EXEC) ./it.sh && echo '--> Testing ${SELF}  [DONE]'

run-test:
	@$(MAKE) URL=${URL} -C ../test run-test

all: clean build test
