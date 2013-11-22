.PHONY: all build test

HERE=`pwd`
THERE="${HERE}/../"

all: build test

build:
	@echo HERE=${HERE}
	@echo THERE=${THERE}
	@mvn clean verify

test:
	$(MAKE) -C serverside-todomvc-wicket/ test
