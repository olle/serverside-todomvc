.PHONY: build clean

build:
	@mvn verify

clean:
	@mvn clean

## TODO: This here below, was the old boostrap for starting and running tests.
##       Unfortunately, it's not very smart - doesn't spawn a process to provide
##       the actual start/stop control that we need. Need to rethink this.
## 
##       A-U-T-O-M-A-T-E!!!
##
#THIS="serverside-todomvc-java-wicket"
#HERE=`pwd`
#THERE="${HERE}/../"
#export URL="http://localhost:8080/${THIS}/"
#test:
#	@java -jar target/*.jar
#	$(MAKE) -C ${THERE} test
