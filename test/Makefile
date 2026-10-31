.PHONY: all run-test clean-test

URL ?= http://localhost:8080

all: run-test

run-test:
	@./node_modules/.bin/cypress run --config baseUrl=$(URL)

clean-test:
	@rm -rf cypress/screenshots cypress/videos cypress/downloads cypress/reports *.xml *.log 2>/dev/null || true
	@echo "Cleaned test artifacts"
