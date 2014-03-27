### 
### Server-side TodoMVC - Java Examples
###
### This is the `Makefile` for the Java examples. This is where we want to
### provide a one-stop-shop for building and testing _all_ the examples in
### Java - so we've choosen `make` to do it.
###
### NOTE: Most of the Java projects are of course built using the old suck
###       ass `Maven`. After some mature consideration (and a buttload of
###       problems) we've left the path of doing a modular build. It's
###       probably for the better since we cannot safely predict what type
###       of build and test-tools that will be required in the future.
###
### So here you go, hack away and please add any new projects to this file.
###
.PHONY: all

###
### The `all` target should just delegate into each of the examples
###
all:
	mvn clean verify

