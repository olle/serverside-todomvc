.PHONY: build test

THIS="serverside-todomvc-wicket"
HERE=`pwd`
THERE="${HERE}/../../"
export URL="http://localhost:8080/${THIS}/"

build:
	@mvn verify -f ../pom.xml -pl ${THIS} -am

test:
	@java -jar target/*.jar
	$(MAKE) -C ${THERE} test
