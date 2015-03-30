Java Servlet Example
====================

This is a classic. What could be simpler than some sticky Java-code all served
up through the Servlet-API. Well, even though it might be a bit bare, the goal
here is to spice up the implementation.

## Model View Controller

A proper model. There's no need for any persistance other than what is running
in memory. A collection of tasks or _todo-items_ is actually all we need. We
will start by modeling that, and then moving on to the tasks. Our goal is to 
use as modern code as possible - let's see what we can do.

## Getting started

To get started you have to have `java` and Maven (`mvn`) installed, then it
should just be a matter of running:

    mvn jetty:run
    
Happy hacking!