To build and run this project, install the example's local tooling with
[mise](https://mise.jdx.dev):

   > mise install

Then build and start the example:

   > make build
   > make start

This starts the local native TodoMVC socket-server at the address
http://localhost:8989/.

You can also run Maven and Java directly through mise:

   > mise exec -- mvn clean package
   > mise exec -- java -jar target/examples-java-native.jar

Tool versions are declared in `mise.toml`. Update them there and run
`mise install` to refresh the local toolchain.

That's it!
